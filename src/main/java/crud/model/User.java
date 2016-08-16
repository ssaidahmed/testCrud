package crud.model;




import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "isAdmin")
    private Boolean isAdmin;
    @Column(name = "createDate")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private DateTime createDate;

    public void setId(long id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setCreateDate(DateTime createDate) {
        this.createDate = createDate;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }


    public DateTime getCreateDate(){
        return createDate;
    }
    @Transient
    public String getCreateDateString() {
        String createDateString = "";
        if(createDate != null){
            createDateString = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").print(createDate);
        }
        return createDateString;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
