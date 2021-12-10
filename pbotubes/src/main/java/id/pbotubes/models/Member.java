package id.pbotubes.models;

public class Member {
    private int id;
    private String username;
    private String alamat;
    private  String namaLengkap;
    private String password;

    public Member(int id, String username, String alamat, String namaLengkap) {
        this.id = id;
        this.username = username;
        this.alamat = alamat;
        this.namaLengkap = namaLengkap;
    }

    public Member() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
