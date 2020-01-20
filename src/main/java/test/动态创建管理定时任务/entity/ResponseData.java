package test.动态创建管理定时任务.entity;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import org.springframework.http.ResponseEntity;

public class ResponseData<T> {
    public int code;

    public Boolean success;

    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ResponseData() {
        super();
        this.code = 0;
        this.success = Boolean.TRUE;
    }

    public ResponseData(T data) {
        super();
        this.code = 0;
        this.success = Boolean.TRUE;
        this.data = data;
    }

    public ResponseData(int code, Boolean success, T data) {
        super();
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public static <T> ResponseEntity<?> ok() {
        return ResponseEntity.ok(new ResponseData());
    }

    public static <T> ResponseEntity<?> ok(T data) {
        return ResponseEntity.ok(new ResponseData(data));
    }

    public static ResponseEntity<?> error() {
        return ResponseEntity.ok(new ResponseData(-1, false, null));
    }

    public static ResponseEntity<?> error(String errorCode) {
        return ResponseEntity.ok(new ResponseData(-1, false, errorCode));
    }

    public static ResponseEntity<?> error(Throwable t) {
        return ResponseEntity.ok(new ResponseData(-1, false, t));
    }

}
