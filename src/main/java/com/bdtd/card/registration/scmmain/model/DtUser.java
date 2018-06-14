package com.bdtd.card.registration.scmmain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 卡管理-用户表
 * </p>
 *
 * @author lilei123
 * @since 2018-06-13
 */
@Entity
@TableName("dt_user")
public class DtUser extends Model<DtUser> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="user_serial")
    private Long userSerial;
    @Column(name="user_no")
    private String userNo;
    @Column(name="user_lname")
    private String userLname;
    @Column(name="user_fname")
    private String userFname;
    @Column(name="dep_no")
    private String depNo;
    @Column(name="user_dep")
    private Integer userDep;
    @Column(name="user_depname")
    private String userDepname;
    @Column(name="user_workday")
    private Date userWorkday;
    @Column(name="user_duty")
    private String userDuty;
    @Column(name="user_card")
    private String userCard;
    @Column(name="user_finger")
    private String userFinger;
    @Column(name="user_password")
    private String userPassword;
    @Column(name="pwd_begin")
    private Date pwdBegin;
    @Column(name="pwd_end")
    private Date pwdEnd;
    @Column(name="user_type")
    private Integer userType;
    @Column(name="pact_begin")
    private Date pactBegin;
    @Column(name="pact_end")
    private Date pactEnd;
    @Column(name="user_level")
    private Integer userLevel;
    @Column(name="user_photo")
    private Integer userPhoto;
    @Column(name="user_sex")
    private String userSex;
    @Column(name="user_nation")
    private String userNation;
    @Column(name="user_xueli")
    private String userXueli;
    @Column(name="user_native")
    private String userNative;
    @Column(name="user_birthday")
    private Date userBirthday;
    @Column(name="user_id")
    private String userId;
    @Column(name="user_post")
    private String userPost;
    @Column(name="user_linkman")
    private String userLinkman;
    @Column(name="user_telephone")
    private String userTelephone;
    @Column(name="user_address")
    private String userAddress;
    @Column(name="user_email")
    private String userEmail;
    @Column(name="user_1")
    private String user1;
    @Column(name="user_2")
    private String user2;
    @Column(name="user_bz")
    private String userBz;
    @Column(name="kq_rule")
    private String kqRule;
    @Column(name="kq_taoban")
    private String kqTaoban;
    @Column(name="kq_tiaoxiu")
    private Integer kqTiaoxiu;
    @Column(name="xf_time")
    private Date xfTime;
    @Column(name="xf_jiange")
    private Integer xfJiange;
    @Column(name="xf_je")
    private Integer xfJe;
    @Column(name="user_sj")
    private Date userSj;
    @Column(name="user_rank")
    private String userRank;
    @Column(name="gly_img")
    private Integer glyImg;
    @Column(name="user_ac")
    private Integer userAc;
    @Column(name="user_gsbh")
    private String userGsbh;
    @Column(name="user_yglx")
    private String userYglx;
    @Column(name="user_zhbh")
    private String userZhbh;
    @Column(name="user_zhlx")
    private String userZhlx;
    @Column(name="user_txm")
    private String userTxm;
    @Column(name="user_lx")
    private Integer userLx;
    @Column(name="user_mj")
    private Integer userMj;


    public Long getUserSerial() {
        return userSerial;
    }

    public void setUserSerial(Long userSerial) {
        this.userSerial = userSerial;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public Integer getUserDep() {
        return userDep;
    }

    public void setUserDep(Integer userDep) {
        this.userDep = userDep;
    }

    public String getUserDepname() {
        return userDepname;
    }

    public void setUserDepname(String userDepname) {
        this.userDepname = userDepname;
    }

    public Date getUserWorkday() {
        return userWorkday;
    }

    public void setUserWorkday(Date userWorkday) {
        this.userWorkday = userWorkday;
    }

    public String getUserDuty() {
        return userDuty;
    }

    public void setUserDuty(String userDuty) {
        this.userDuty = userDuty;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getUserFinger() {
        return userFinger;
    }

    public void setUserFinger(String userFinger) {
        this.userFinger = userFinger;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Date getPwdBegin() {
        return pwdBegin;
    }

    public void setPwdBegin(Date pwdBegin) {
        this.pwdBegin = pwdBegin;
    }

    public Date getPwdEnd() {
        return pwdEnd;
    }

    public void setPwdEnd(Date pwdEnd) {
        this.pwdEnd = pwdEnd;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getPactBegin() {
        return pactBegin;
    }

    public void setPactBegin(Date pactBegin) {
        this.pactBegin = pactBegin;
    }

    public Date getPactEnd() {
        return pactEnd;
    }

    public void setPactEnd(Date pactEnd) {
        this.pactEnd = pactEnd;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Integer userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserNation() {
        return userNation;
    }

    public void setUserNation(String userNation) {
        this.userNation = userNation;
    }

    public String getUserXueli() {
        return userXueli;
    }

    public void setUserXueli(String userXueli) {
        this.userXueli = userXueli;
    }

    public String getUserNative() {
        return userNative;
    }

    public void setUserNative(String userNative) {
        this.userNative = userNative;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    public String getUserLinkman() {
        return userLinkman;
    }

    public void setUserLinkman(String userLinkman) {
        this.userLinkman = userLinkman;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getUserBz() {
        return userBz;
    }

    public void setUserBz(String userBz) {
        this.userBz = userBz;
    }

    public String getKqRule() {
        return kqRule;
    }

    public void setKqRule(String kqRule) {
        this.kqRule = kqRule;
    }

    public String getKqTaoban() {
        return kqTaoban;
    }

    public void setKqTaoban(String kqTaoban) {
        this.kqTaoban = kqTaoban;
    }

    public Integer getKqTiaoxiu() {
        return kqTiaoxiu;
    }

    public void setKqTiaoxiu(Integer kqTiaoxiu) {
        this.kqTiaoxiu = kqTiaoxiu;
    }

    public Date getXfTime() {
        return xfTime;
    }

    public void setXfTime(Date xfTime) {
        this.xfTime = xfTime;
    }

    public Integer getXfJiange() {
        return xfJiange;
    }

    public void setXfJiange(Integer xfJiange) {
        this.xfJiange = xfJiange;
    }

    public Integer getXfJe() {
        return xfJe;
    }

    public void setXfJe(Integer xfJe) {
        this.xfJe = xfJe;
    }

    public Date getUserSj() {
        return userSj;
    }

    public void setUserSj(Date userSj) {
        this.userSj = userSj;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public Integer getGlyImg() {
        return glyImg;
    }

    public void setGlyImg(Integer glyImg) {
        this.glyImg = glyImg;
    }

    public Integer getUserAc() {
        return userAc;
    }

    public void setUserAc(Integer userAc) {
        this.userAc = userAc;
    }

    public String getUserGsbh() {
        return userGsbh;
    }

    public void setUserGsbh(String userGsbh) {
        this.userGsbh = userGsbh;
    }

    public String getUserYglx() {
        return userYglx;
    }

    public void setUserYglx(String userYglx) {
        this.userYglx = userYglx;
    }

    public String getUserZhbh() {
        return userZhbh;
    }

    public void setUserZhbh(String userZhbh) {
        this.userZhbh = userZhbh;
    }

    public String getUserZhlx() {
        return userZhlx;
    }

    public void setUserZhlx(String userZhlx) {
        this.userZhlx = userZhlx;
    }

    public String getUserTxm() {
        return userTxm;
    }

    public void setUserTxm(String userTxm) {
        this.userTxm = userTxm;
    }

    public Integer getUserLx() {
        return userLx;
    }

    public void setUserLx(Integer userLx) {
        this.userLx = userLx;
    }

    public Integer getUserMj() {
        return userMj;
    }

    public void setUserMj(Integer userMj) {
        this.userMj = userMj;
    }

    @Override
    protected Serializable pkVal() {
        return this.userSerial;
    }

    @Override
    public String toString() {
        return "User{" +
        "userSerial=" + userSerial +
        ", userNo=" + userNo +
        ", userLname=" + userLname +
        ", userFname=" + userFname +
        ", depNo=" + depNo +
        ", userDep=" + userDep +
        ", userDepname=" + userDepname +
        ", userWorkday=" + userWorkday +
        ", userDuty=" + userDuty +
        ", userCard=" + userCard +
        ", userFinger=" + userFinger +
        ", userPassword=" + userPassword +
        ", pwdBegin=" + pwdBegin +
        ", pwdEnd=" + pwdEnd +
        ", userType=" + userType +
        ", pactBegin=" + pactBegin +
        ", pactEnd=" + pactEnd +
        ", userLevel=" + userLevel +
        ", userPhoto=" + userPhoto +
        ", userSex=" + userSex +
        ", userNation=" + userNation +
        ", userXueli=" + userXueli +
        ", userNative=" + userNative +
        ", userBirthday=" + userBirthday +
        ", userId=" + userId +
        ", userPost=" + userPost +
        ", userLinkman=" + userLinkman +
        ", userTelephone=" + userTelephone +
        ", userAddress=" + userAddress +
        ", userEmail=" + userEmail +
        ", user1=" + user1 +
        ", user2=" + user2 +
        ", userBz=" + userBz +
        ", kqRule=" + kqRule +
        ", kqTaoban=" + kqTaoban +
        ", kqTiaoxiu=" + kqTiaoxiu +
        ", xfTime=" + xfTime +
        ", xfJiange=" + xfJiange +
        ", xfJe=" + xfJe +
        ", userSj=" + userSj +
        ", userRank=" + userRank +
        ", glyImg=" + glyImg +
        ", userAc=" + userAc +
        ", userGsbh=" + userGsbh +
        ", userYglx=" + userYglx +
        ", userZhbh=" + userZhbh +
        ", userZhlx=" + userZhlx +
        ", userTxm=" + userTxm +
        ", userLx=" + userLx +
        ", userMj=" + userMj +
        "}";
    }
}
