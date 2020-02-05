package ru.informatics.jojomafia.protocol;

public class Response<T> {

    private Header header;
    private T data;

    public Response() {
        this.header = new Header();
    }

    public T getData() {
        return data;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setHeaderName(String name) {
        this.header.setName(name);
    }


    public void setHeaderType(String type) {
        this.header.setType(type);
    }

    public String getHeaderName() {
        return this.header.getName();
    }

    public String getHeaderType() {
        return this.header.getType();
    }

    @Override
    public String toString() {
        return
                "{" +
                        "header: {" +
                        "\"name\"=\"" + getHeaderName() + "\", " +
                        "\"type\"=\"" + getHeaderType() + "\" " +
                        "}" +
                        "data: " + getData() + "" +
                        "}";
    }

    public static <E> Response<E> build(E data) {
        Response response = new Response();
        response.setData(data);
        return response;
    }
}

class Header {

    private String name;
    private String type;

    public Header() {
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

}
