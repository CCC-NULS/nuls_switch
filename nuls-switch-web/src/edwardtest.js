'use strict';

var nuls = require('nuls-sdk-js/lib/api/sdk');
var nuls2 = require('nuls-sdk-js/lib');
var Serializers = require("nuls-sdk-js/lib/api/serializers");
var newAddressInfo = nuls2.newAddress(2, "123456");
var pri = nuls.decrypteOfAES(newAddressInfo.aesPri, "123456");
//console.log(pri)
//console.log(newAddressInfo['pub'])
// var digest = nuls.signature("616466616466616466",pri)
// console.log(digest)


var buffer = require('nuls-sdk-js/lib/utils/buffer');
var txs = require('nuls-sdk-js/src/model/txs');
var nuls_sdk_js_lib = require('nuls-sdk-js/lib');

function utf8ByteToUnicodeStr(utf8Bytes) {
    var unicodeStr = "";
    for (var pos = 0; pos < utf8Bytes.length;) {
        var flag = utf8Bytes[pos];
        var unicode = 0;
        if ((flag >>> 7) === 0) {
            unicodeStr += String.fromCharCode(utf8Bytes[pos]);
            pos += 1;
        } else if ((flag & 0xFC) === 0xFC) {
            unicode = (utf8Bytes[pos] & 0x3) << 30;
            unicode |= (utf8Bytes[pos + 1] & 0x3F) << 24;
            unicode |= (utf8Bytes[pos + 2] & 0x3F) << 18;
            unicode |= (utf8Bytes[pos + 3] & 0x3F) << 12;
            unicode |= (utf8Bytes[pos + 4] & 0x3F) << 6;
            unicode |= (utf8Bytes[pos + 5] & 0x3F);
            unicodeStr += String.fromCharCode(unicode);
            pos += 6;
        } else if ((flag & 0xF8) === 0xF8) {
            unicode = (utf8Bytes[pos] & 0x7) << 24;
            unicode |= (utf8Bytes[pos + 1] & 0x3F) << 18;
            unicode |= (utf8Bytes[pos + 2] & 0x3F) << 12;
            unicode |= (utf8Bytes[pos + 3] & 0x3F) << 6;
            unicode |= (utf8Bytes[pos + 4] & 0x3F);
            unicodeStr += String.fromCharCode(unicode);
            pos += 5;
        } else if ((flag & 0xF0) === 0xF0) {
            unicode = (utf8Bytes[pos] & 0xF) << 18;
            unicode |= (utf8Bytes[pos + 1] & 0x3F) << 12;
            unicode |= (utf8Bytes[pos + 2] & 0x3F) << 6;
            unicode |= (utf8Bytes[pos + 3] & 0x3F);
            unicodeStr += String.fromCharCode(unicode);
            pos += 4;
        } else if ((flag & 0xE0) === 0xE0) {
            unicode = (utf8Bytes[pos] & 0x1F) << 12;
            unicode |= (utf8Bytes[pos + 1] & 0x3F) << 6;
            unicode |= (utf8Bytes[pos + 2] & 0x3F);
            unicodeStr += String.fromCharCode(unicode);
            pos += 3;
        } else if ((flag & 0xC0) === 0xC0) { //110
            unicode = (utf8Bytes[pos] & 0x3F) << 6;
            unicode |= (utf8Bytes[pos + 1] & 0x3F);
            unicodeStr += String.fromCharCode(unicode);
            pos += 2;
        } else {
            unicodeStr += String.fromCharCode(utf8Bytes[pos]);
            pos += 1;
        }
    }
    return unicodeStr;
}

let VarInt = function () {
    this.value = null;
    this.originallyEncodedSize = 0;//交易类型
    this.parse = function (buf, offset) {
        let first = 0xFF & buf[offset];
        if (first < 253) {
            this.value = first;
            // 1 entity byte (8 bits)
            this.originallyEncodedSize = 1;
        } else if (first == 253) {
            this.value = (0xFF & buf[offset + 1]) | ((0xFF & buf[offset + 2]) << 8);
            // 1 marker + 2 entity bytes (16 bits)
            this.originallyEncodedSize = 3;
        } else if (first == 254) {
            this.value = buf.readUInt32LE(offset + 1);
            // 1 marker + 4 entity bytes (32 bits)
            this.originallyEncodedSize = 5;
        } else {
            this.value = buf.readBigInt64LE(offset + 1);
            // 1 marker + 8 entity bytes (64 bits)
            this.originallyEncodedSize = 9;
        }
    };


};
//转为字节
let test = buffer.hexToBuffer("0200a502475d0000fd1601021702000148efaf3aa2c3258b6fd2901df4dc711d13caad7802000100a0509c3b000000000000000000000000000000000000000000000000000000000800000000000000000017020001560612e2b5523951628bd6d5d8a464e70b502f2d02000100a01a377700000000000000000000000000000000000000000000000000000000080000000000000000000217020001560612e2b5523951628bd6d5d8a464e70b502f2d0200010000ca9a3b0000000000000000000000000000000000000000000000000000000000000000000000001702000148efaf3aa2c3258b6fd2901df4dc711d13caad7802000100009435770000000000000000000000000000000000000000000000000000000000000000000000006a210264315d998494d2f41a9fa4a2dcbf28f3f4dd6ad839898e3c97ec00f927fabc2c473045022100cc0e615b19b583645e24b9366db31257e9099f08c4ae3e2f9ba67c508c03f41202200cda2c8e95b82c91b40da5e5c71a91b54756c75221a246f4e2e3a3046fc48543");
let transaction = new txs.TransferTransaction();
let offset = 0;
//读取type
transaction.type = test.readInt16LE(offset);
offset += 2;
transaction.time = test.readInt32LE(offset);
offset += 4;
let varInt = new VarInt();
varInt.parse(test, offset);
if (varInt.value == 0) {
    transaction.remark = null;
}
offset += varInt.originallyEncodedSize;
varInt.parse(test, offset);
offset += varInt.originallyEncodedSize;
transaction.txData = test.slice(offset, varInt.value);
offset += varInt.value;
varInt.parse(test, offset);
offset += varInt.originallyEncodedSize;
transaction.coinData = test.slice(offset, offset + varInt.value);

offset += varInt.value;
varInt.parse(test, offset);
offset += varInt.originallyEncodedSize;
transaction.signatures = test.slice(offset, offset + varInt.value);

//追加签名
let bw = new Serializers();
bw.getBufWriter().write(transaction.signatures);
let transactionSignature = nuls_sdk_js_lib.transactionSignature(pri, transaction);
bw.writeBytesWithLength(buffer.hexToBuffer(newAddressInfo['pub']));
bw.writeBytesWithLength(transactionSignature);
transaction.signatures = bw.getBufWriter().toBuffer();
let transactionHex = transaction.txSerialize().toString('hex');
console.log(transactionHex)






