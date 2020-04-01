package co.com.jwtapp.authenticationapi.models.generic;

public class DataContainer<T> {

    private T data;

    public DataContainer() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
