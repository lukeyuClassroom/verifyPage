package nbps.net.verifypage.controller;

import nbps.net.verifypage.global.VerifyCodeHolder;
import nbps.net.verifypage.utils.VerifyCodeUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class CommonController {
    @Autowired
    VerifyCodeHolder holder;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("verifypage");
    }

    @GetMapping("/staffservice/sign/VerifiPage")
    public String verifyPage() {
        return "verifypage";
    }

    @GetMapping("/verifycode")
    public void getAuthCodeImg(HttpSession session, HttpServletResponse response) {
        String code = VerifyCodeUtil.buildCode(4, false, true, true);
        BufferedImage bufferedImage = VerifyCodeUtil.buildImg(code, 112, 38, 5, 50);
        //存储验证码，用于用户输入校验
        session.setAttribute("code", code);
        holder.setVc(code);
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

    @PostMapping("/staffservice/sign/api/huawei/browse/verifiCode")
    @ResponseBody
    public Data verify(@RequestParam("verificode") String verificode, @RequestParam("validatecode") String validatecode){
        Data data = new Data();
        if (!holder.getVc().equalsIgnoreCase(validatecode)){
            data.setIssuccess("0");
            data.setMsg("验证码错误");
            return data;
        }
        if (!"C01019202112210009".equalsIgnoreCase(verificode)) {
            data.setIssuccess("0");
            data.setMsg("检验失败，证明编号不匹配，请重新输入");
            return data;
        }

        data.setIssuccess("1");
        data.setUrl("Thttp://www.nbps.net/getZM?vc=C01019202112210009");
        return data;
    }

    @GetMapping("/getZM")
    public void getZM(HttpSession session, HttpServletResponse response) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/年收入证明（标准）20211221.pdf");
        InputStream inputStream = classPathResource.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
    }
}

class Data{
    String issuccess;
    String msg;
    String url;

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
