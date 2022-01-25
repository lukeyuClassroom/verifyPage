package nbps.net.verifypage;

import nbps.net.verifypage.utils.VerifyCodeUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wumingwang
 */
@SpringBootApplication
@Controller
public class VerifyPageApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VerifyPageApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(VerifyPageApplication.class, args);
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("verifypage");
    }

    @GetMapping("/VerifyPage")
    public String verifyPage() {
        return "verifypage";
    }

    @GetMapping("/verifycode")
    public void getAuthCodeImg(HttpSession session, HttpServletResponse response) {
        String code = VerifyCodeUtil.buildCode(4, false, true, true);
        BufferedImage bufferedImage = VerifyCodeUtil.buildImg(code, 112, 38, 5, 50);
        //存储验证码，用于用户输入校验
        session.setAttribute("code", code);
        //禁止图片缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //设置响应格式
        response.setContentType("image/jpeg");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpeg", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
