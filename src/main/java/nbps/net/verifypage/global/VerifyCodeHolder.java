package nbps.net.verifypage.global;

import org.springframework.stereotype.Component;

@Component
public class VerifyCodeHolder {
    private String vc;

    public String getVc() {
        return vc;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }
}
