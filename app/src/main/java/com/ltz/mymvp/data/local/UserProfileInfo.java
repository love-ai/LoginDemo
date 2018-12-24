package com.ltz.mymvp.data.local;

import com.ltz.mymvp.data.db.MyDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MyDataBase.class)
public class UserProfileInfo extends BaseModel{
    private static final long serialVersionUID = -1749819189773539236L;
    @Column
    private int type;
    @Column
    private int status;
    @Column
    private String name;
    @PrimaryKey
    private String mobile;
    @Column
    private String email;
    @Column
    private String realname;
    @Column
    private String create_time;
    @Column
    private String id_card_no;
    @Column
    private int id_card_type;
    @Column
    private int realname_status;
    @Column
    private int is_auto_bid;
    @Column
    private int inviter;
    @Column
    private String pcode;
    @Column
    private int bindcard_status;
    @Column
    private String vip_title;

    public int getId_card_type() {
        return id_card_type;
    }

    public void setId_card_type(int id_card_type) {
        this.id_card_type = id_card_type;
    }

    public String getVip_title() {
        return vip_title;
    }

    public void setVip_title(String vip_title) {
        this.vip_title = vip_title;
    }

    public String getBankcard_display_text() {
        return bankcard_display_text;
    }

    public void setBankcard_display_text(String bankcard_display_text) {
        this.bankcard_display_text = bankcard_display_text;
    }
    @Column
    private String bankcard_display_text;
    @Column
    private String birthday;
    @Column
    private String bless_name;
    @Column
    private String bless_bg;
    @Column
    private String bless_url;
    @Column
    private String avatar_url;
    @Column
    private String risk_type;
    @Column
    private int has_emergency_contact;

    @Column
    private int amount;
    @Column
    private String user_name_info;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId_card_no() {
        return id_card_no;
    }

    public void setId_card_no(String id_card_no) {
        this.id_card_no = id_card_no;
    }


    public int getRealname_status() {
        return realname_status;
    }

    public void setRealname_status(int realname_status) {
        this.realname_status = realname_status;
    }

    public int getIs_auto_bid() {
        return is_auto_bid;
    }

    public void setIs_auto_bid(int is_auto_bid) {
        this.is_auto_bid = is_auto_bid;
    }

    public int getInviter() {
        return inviter;
    }

    public void setInviter(int inviter) {
        this.inviter = inviter;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public int getBindcard_status() {
        return bindcard_status;
    }

    public void setBindcard_status(int bindcard_status) {
        this.bindcard_status = bindcard_status;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBless_name() {
        return bless_name;
    }

    public void setBless_name(String bless_name) {
        this.bless_name = bless_name;
    }

    public String getBless_bg() {
        return bless_bg;
    }

    public void setBless_bg(String bless_bg) {
        this.bless_bg = bless_bg;
    }

    public String getBless_url() {
        return bless_url;
    }

    public void setBless_url(String bless_url) {
        this.bless_url = bless_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRisk_type() {
        return risk_type;
    }

    public void setRisk_type(String risk_type) {
        this.risk_type = risk_type;
    }

    public int getHas_emergency_contact() {
        return has_emergency_contact;
    }

    public void setHas_emergency_contact(int has_emergency_contact) {
        this.has_emergency_contact = has_emergency_contact;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUser_name_info() {
        return user_name_info;
    }

    public void setUser_name_info(String user_name_info) {
        this.user_name_info = user_name_info;
    }
}
