package id.pbotubes;

public class MemberModel {
    private int id;
    private  String username;
    private String pasword;
    private int role;
    private  String namaLengkap;
    private String alamat;


    public MemberModel(int id, String username, String pasword, int role, String namaLengkap, String alamat) {
        this.id = id;
        this.username = username;
        this.pasword = pasword;
        this.role = role;
        this.namaLengkap = namaLengkap;
        this.alamat = alamat;
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

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
