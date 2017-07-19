package yeyeapp.in.mytestproject.model.RetrofitData;

import java.io.Serializable;

/**
 * Created by yusheng on 2017/5/9.
 */

public class RetrofitResult<T>  implements Serializable {
    private int errcode;
    private String errmessage;
    private T data;

    public boolean isSuccess() {
        return errcode == 0 ? true : false;
    }

    public String getErrmessage() {
        return errmessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "result = \n" +
                "errcode = " + errcode + "\n" +
                "errmessage = " + errmessage + "\n" +
                "data = " + data;
    }
}
