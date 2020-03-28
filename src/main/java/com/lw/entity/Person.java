package com.lw.entity;

public class Person {
    private String id;
    //0,1,2,3,4,5
    private String type;
    private String name;
    private String gender;
    private String username;
    private String idCard;
    private String age;
    private String password;
    private String nickname;
    private String pic;
    private String height;
    private String weight;
    private String description;
    private String verify;
    private String createTime;
    private String groupId;
    private String groupName;
    private String status;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Person() {
    }

    public Person(String id, String type, String name,
                  String gender, String username, String idCard,
                  String age, String password, String nickname, String pic,
                  String height, String weight, String description, String verify,
                  String createTime,String groupId,String groupName) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.username = username;
        this.idCard = idCard;
        this.age = age;
        this.password = password;
        this.nickname = nickname;
        this.pic = pic;
        this.height = height;
        this.weight = weight;
        this.description = description;
        this.verify = verify;
        this.createTime = createTime;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
