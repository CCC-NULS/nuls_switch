package io.nuls.nulsswitch.common.utils;

import io.nuls.nulsswitch.common.config.Constant;
import io.nuls.nulsswitch.common.domain.ConfigDO;
import io.nuls.nulsswitch.common.exception.IFastException;
import io.nuls.nulsswitch.common.service.ConfigService;
import io.nuls.nulsswitch.common.type.EnumErrorCode;
import io.nuls.nulsswitch.generator.domain.ColumnDO;
import io.nuls.nulsswitch.generator.domain.TableDO;
import io.nuls.nulsswitch.generator.type.EnumGen;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器 工具类
 */
public class GenUtils {

    public static String STR_DELIMITER = "_";

    private static Logger log = LoggerFactory.getLogger(GenUtils.class);

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("templates/common/generator/domain.java.vm");
        templates.add("templates/common/generator/Dao.java.vm");
        templates.add("templates/common/generator/Mapper.xml.vm");
        templates.add("templates/common/generator/Service.java.vm");
        templates.add("templates/common/generator/ServiceImpl.java.vm");
        templates.add("templates/common/generator/Controller.java.vm");
        templates.add("templates/common/generator/list.html.vm");
        templates.add("templates/common/generator/add.html.vm");
        templates.add("templates/common/generator/edit.html.vm");
        templates.add("templates/common/generator/list.js.vm");
        templates.add("templates/common/generator/add.js.vm");
        templates.add("templates/common/generator/edit.js.vm");
        templates.add("templates/common/generator/menu.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     */

    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns,
            ZipOutputStream zip) {
        // 配置信息
        Map<String, String> config = getConfig();
        // 表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        // 表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.get("tablePrefix"), config.get("autoRemovePre"));
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));

        // 列信息
        List<ColumnDO> columsList = new ArrayList<>();
        Set<String> dataTypes = new HashSet<>(), columnNames = new HashSet<>();
        List<String> baseColumnNames = Arrays.asList("deleted", "version", "createAt", "createBy", "updateAt", "updateBy");
        for (Map<String, String> column : columns) {
        	columnNames.add(column.get("columnName"));
        	if(baseColumnNames.contains(column.get("columnName"))) {
        	    continue;
            }
        	
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));

            // 列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = config.get(columnDO.getDataType());
            columnDO.setAttrType(attrType);

            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
            dataTypes.add(column.get("dataType"));
        }
        tableDO.setColumns(columsList);

        // 没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        // 封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        String pack = config.get("package");
        map.put("pathName", pack.substring(pack.lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", pack);
        map.put("author", config.get("author"));
        map.put("email", config.get("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_19));
        // 字段特性
        map.put("hasBigDecimal", dataTypes.contains("decimal"));
        //时间类型的包含3种，date、datetime、timestamp
        if (dataTypes.contains("date") || dataTypes.contains("datetime") || dataTypes.contains("timestamp")) {
            map.put("hasDatetime", 1);
        }
        map.put("hasDeleted", columnNames.contains("deleted"));
        map.put("hasVersion", columnNames.contains("version"));
        map.put("hasCreateAt", columnNames.contains("createAt"));
        map.put("hasCreateBy", columnNames.contains("createBy"));
        map.put("hasUpdateAt", columnNames.contains("updateAt"));
        map.put("hasUpdateBy", columnNames.contains("updateBy"));
        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableDO.getClassname(), tableDO.getClassName(),
                        pack.substring(pack.lastIndexOf(".") + 1))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                log.warn(e.getMessage());
                log.info("渲染模板失败，表名：" + tableDO.getTableName());
                throw new IFastException(EnumErrorCode.genRenderTemplateError.getCodeStr());
            }
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        if(columnName.contains(STR_DELIMITER)){
            return WordUtils.capitalize(columnName, new char[] { '_' }).replace(STR_DELIMITER, "");
        }else {
            return WordUtils.capitalize(columnName);
        }
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {

        if (Constant.Generator.AUTO_REMOVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf(STR_DELIMITER) + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Map<String, String> getConfig() {
        ConfigService configService = SpringContextHolder.getBean(ConfigService.class);
        List<ConfigDO> list = configService.findListByKvType(EnumGen.KvType.base.getValue());
        list.addAll(configService.findListByKvType(EnumGen.KvType.mapping.getValue()));
        Map<String, String> config = new HashMap<>();
        list.stream().forEach(kv -> config.put(kv.getK(), kv.getV()));
        return config;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("domain.java.vm")) {
            return packagePath + "domain" + File.separator + className + "DO.java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + packageName
                    + File.separator + className + "Mapper.xml";
        }

        if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
                    + File.separator + classname + File.separator + classname + ".html";
        }
        if (template.contains("add.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
                    + File.separator + classname + File.separator + "add.html";
        }
        if (template.contains("edit.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
                    + File.separator + classname + File.separator + "edit.html";
        }

        if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
                    + File.separator + "appjs" + File.separator + packageName + File.separator + classname
                    + File.separator + classname + ".js";
        }
        if (template.contains("add.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
                    + File.separator + "appjs" + File.separator + packageName + File.separator + classname
                    + File.separator + "add.js";
        }
        if (template.contains("edit.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
                    + File.separator + "appjs" + File.separator + packageName + File.separator + classname
                    + File.separator + "edit.js";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}
