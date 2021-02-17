package com.szhbl.common.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author HP
 */
@Slf4j
public class PdfTableMehhod {

    //页码事件
    public static class PageXofYTest extends PdfPageEventHelper {
        /**
         * The PdfTemplate that contains the total number of pages.
         */
        public PdfTemplate total;

        public BaseFont bfChinese;

        /**
         * 重写PdfPageEventHelper中的onOpenDocument方法
         */
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            // 得到文档的内容并为该内容新建一个模板
            total = writer.getDirectContent().createTemplate(500, 500);
            try {
                String prefixFont = "";
                String os = System.getProperties().getProperty("os.name");
                if (os.startsWith("win") || os.startsWith("Win")) {
                    prefixFont = "C:\\Windows\\Fonts" + File.separator;
                } else {
                    prefixFont = "/usr/share/fonts/chinese" + File.separator;
                }
                // 设置字体对象为Windows系统默认的字体
                bfChinese = BaseFont.createFont(prefixFont + "simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        }

        /**
         * 重写PdfPageEventHelper中的onEndPage方法
         */
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // 新建获得用户页面文本和图片内容位置的对象
            PdfContentByte pdfContentByte = writer.getDirectContent();
            // 保存图形状态
            pdfContentByte.saveState();
            String text = writer.getPageNumber() + "/";
            // 获取点字符串的宽度
            float textSize = bfChinese.getWidthPoint(text, 9);
            pdfContentByte.beginText();
            // 设置随后的文本内容写作的字体和字号
            pdfContentByte.setFontAndSize(bfChinese, 9);

            // 定位'X/'
            float x = (document.right() + document.left()) / 2;
            float y = 56f;
            pdfContentByte.setTextMatrix(x, y);
            pdfContentByte.showText(text);
            pdfContentByte.endText();

            // 将模板加入到内容（content）中- // 定位'Y'
            pdfContentByte.addTemplate(total, x + textSize, y);

            pdfContentByte.restoreState();
        }

        /**
         * 重写PdfPageEventHelper中的onCloseDocument方法
         */
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            total.beginText();
            try {
                String prefixFont = "";
                String os = System.getProperties().getProperty("os.name");
                if (os.startsWith("win") || os.startsWith("Win")) {
                    prefixFont = "C:\\Windows\\Fonts" + File.separator;
                } else {
                    prefixFont = "/usr/share/fonts/chinese" + File.separator;
                }

                bfChinese = BaseFont.createFont(prefixFont + "simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                total.setFontAndSize(bfChinese, 9);
            } catch (DocumentException e) {
                log.error("重写PdfPageEventHelper中的onCloseDocument--DocumentException失败：{}",e.toString(),e.getStackTrace());
            } catch (IOException e) {
                log.error("重写PdfPageEventHelper中的onCloseDocument--IOException失败：{}",e.toString(),e.getStackTrace());
            }
            total.setTextMatrix(0, 0);
            // 设置总页数的值到模板上，并应用到每个界面
            total.showText(String.valueOf(writer.getPageNumber() - 1));
            total.endText();
        }
    }

    /**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     *
     * @param value
     * @param font
     * @return
     */
    public static PdfPCell createCell(String value, Font font, boolean border) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..）
     *
     * @param value
     * @param font
     * @param align
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    public static PdfPCell createCell(Image image, Font font, int align, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.addElement(image);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public static PdfPCell createCell_colspan(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    public static PdfPCell createCell_colspan(Image image, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.addElement(image);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x行合并）
     *
     * @param value
     * @param font
     * @param align
     * @param rowspan
     * @return
     */
    public static PdfPCell createCell_rowspan(String value, Font font, int align, int rowspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    public static PdfPCell createCell_rowspan(Image image, Font font, int align, int rowspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setRowspan(rowspan);
        cell.addElement(image);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }

    public static PdfPCell createCell(Image image, int verticalAlignment, int align, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(align);
        cell.addElement(image);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }
    public static PdfPCell createCell_colspan(Image image, int verticalAlignment, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.addElement(image);
        cell.setLeading(PdfConstants.FIXED_LEADING, PdfConstants.MULTIPLIED_LEADING);
        if (!boderFlag) {
            cell.setBorder(0);
        } else if (boderFlag) {
            cell.setBorder(0);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     *
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(PdfConstants.MAX_WIDTH);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            log.error("创建默认列宽，指定列数、水平(居中、右、左)的表格失败：{}",e.toString(),e.getStackTrace());
        }
        return table;
    }

    /**
     * 创建指定列宽、列数的表格
     *
     * @param widths
     * @return
     */
    public static PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(PdfConstants.MAX_WIDTH);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            log.error("创建指定列宽、列数的表格失败：{}",e.toString(),e.getStackTrace());
        }
        return table;
    }

    /**
     * 创建空白的表格
     *
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", PdfConstants.FONT, false));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }

    /**
     * --------------------------创建表格的方法end------------------- ---------
     */

    public Image getBarcodeQRCode(String value) throws BadElementException {
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(value, 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.scaleAbsolute(100, 100);
        return codeQrImage;
    }
}
