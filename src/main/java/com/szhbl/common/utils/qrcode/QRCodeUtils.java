package com.szhbl.common.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author HP
 */
public class QRCodeUtils {

    /**
     * 生成二维码 高宽默认500，默认jpg
     * @param contents 内容
     * @param path 存放地址
     * @param fileName 文件名
     * @throws IOException
     * @throws WriterException
     */
    public static void encodeQRCODE(String contents, String path, String fileName) throws IOException, WriterException {
        String format = "jpg";
        encodeQRCODE(contents,format,path,fileName);
    }
    /**
     * 生成二维码 高宽默认500
     * @param contents 内容
     * @param format 格式
     * @param path 存放地址
     * @param fileName 文件名
     * @throws IOException
     * @throws WriterException
     */
    public static void encodeQRCODE(String contents, String format, String path,String fileName) throws IOException, WriterException {
        int widht = 300;
        int height = 300;
        encodeQRCODE(contents,widht,height,format,path,fileName);
    }

    /**
     *
     * @param contents 内容
     * @param width 宽度
     * @param height 高度
     * @param format 格式
     * @param path 存放地址
     * @param fileName 文件名
     * @throws IOException
     * @throws WriterException
     */
    public static void encodeQRCODE(String contents,  int width, int height, String format, String path,String fileName) throws IOException, WriterException {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置图片的最大值
//		hints.put(EncodeHintType.MAX_SIZE, 350);
        //设置图片的最小值
//	    hints.put(EncodeHintType.MIN_SIZE, 100);
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        //contents要编码的内容; width条形码的宽度; height条形码的高度; hints生成条形码时的一些配置
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
                //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
                //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints);

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 生成二维码
        path = path+"\\"+fileName+"."+format;
        System.out.println(path);
        //指定输出路径
        File outputFile = new File(path);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    }
}
