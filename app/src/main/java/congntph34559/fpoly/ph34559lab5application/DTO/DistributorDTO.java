package congntph34559.fpoly.ph34559lab5application.DTO;

public class DistributorDTO {


    public String _id;
    public String name;

    public DistributorDTO(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public DistributorDTO() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DistributorDTO{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
