package nbps.net.verifypage.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author wumingwang
 */
public class VerifyCodeUtil {
    /**
     * 生成验证码图片
     *
     * @param code       要生成的验证码字符串
     * @param width      图片宽度
     * @param height     图片高度
     * @param lineCount  干扰线数量
     * @param printCount 干扰点数量
     * @return
     */
    public static BufferedImage buildImg(String code, int width, int height, int lineCount, int printCount) {
        char[] codeArray = code.toCharArray();
        //定义随机数
        Random random = new Random();
        //构建图像缓冲区
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        //创建Graphics2D对象
        Graphics2D gp = bufferedImage.createGraphics();
        //设置画笔属性
        BasicStroke bs = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1f);
        gp.setStroke(bs);
        //设置背景
        gp.setColor(new Color(random.nextInt(50) + 205, random.nextInt(50) + 205, random.nextInt(50) + 205));
        gp.fillRect(0, 0, width, height);
        //设置轮廓
        //gp.setColor(Color.BLACK);
        //gp.drawRect(0, 0, width - 1, height - 1);
        //绘制字符
        for (int i = 0; i < codeArray.length; i++) {
            //设置随机字体大小
            int fontSize = random.nextInt(height / 2) + (height / 2);
            Font f = new Font("Arial", random.nextInt(3), fontSize+6);
            gp.setFont(f);
            //计算宽高并绘制
            int avgX = width / codeArray.length;
            gp.setColor(new Color(random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100));
            gp.drawString(String.valueOf((char) codeArray[i]), avgX * i + avgX / (codeArray.length - 1),
                    random.nextInt(height / 2) + (height / 2));
        }
        //绘制干扰线
        for (int j = 0; j < lineCount; j++) {
            int avgX = width / codeArray.length;
            gp.setColor(new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)));
            gp.drawLine(random.nextInt(avgX), random.nextInt(height), random.nextInt(avgX) + avgX * (codeArray.length - 1),
                    random.nextInt(height));
        }
        //绘制干扰点
        for (int z = 0; z < printCount; z++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            gp.drawLine(x, y, x, y);
        }
        return bufferedImage;
    }

    /**
     * 生成随机验证码
     *
     * @param codeSize 验证码中字符数量
     * @param num      是否加入数字0~9
     * @param lc       是否加入小写字母a~z
     * @param uc       是否加入大写字母A~Z
     */
    public static String buildCode(int codeSize, boolean num, boolean lc, boolean uc) {
        StringBuilder code = new StringBuilder();
        StringBuilder schema = new StringBuilder();
        int flag = 0;
        if (num) {
            schema.append('N');
            flag++;
        }
        if (lc) {
            schema.append('L');
            flag++;
        }
        if (uc) {
            schema.append('U');
            flag++;
        }
        char[] arr = schema.toString().toCharArray();
        Random random = new Random();
        for (int i = 0; i < codeSize; i++) {
            char type = 'N';
            if (flag > 0) {
                type = arr[random.nextInt(flag)];
            }
            int ascii = 0;
            switch (type) {
                case 'N':
                    ascii = random.nextInt(10) + 48;
                    break;
                case 'U':
                    ascii = random.nextInt(26) + 65;
                    break;
                case 'L':
                    ascii = random.nextInt(26) + 97;
                    break;
                default:
                    break;
            }
            code.append((char) ascii);
        }
        return code.toString();
    }
}
