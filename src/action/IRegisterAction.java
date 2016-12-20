package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangyu on 12/10/16.
 */
public interface IRegisterAction {
    public String register(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
