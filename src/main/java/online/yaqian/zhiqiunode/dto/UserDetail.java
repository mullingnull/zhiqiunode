package online.yaqian.zhiqiunode.dto;

import online.yaqian.zhiqiunode.model.User;
import online.yaqian.zhiqiunode.model.UserProfile;

/**
 * @author Young
 * @version 创建时间：2016年4月11日 下午5:12:02 类说明：
 */
public class UserDetail{

	private String openId;
	private Integer userId;
	private String nickName;
	private Integer gender;
	private String major;
	private String college;
	private Integer enrolYear;
	private Integer profileId;
	private String wcQrcode;
	private String minor;
	private String am;
	private boolean flag;
	
	private String email;
	private String keyWord;
	private String qqCode;
	private String phone;
	private String wechatId;
	
	public UserDetail() {
		super();
	}

	public UserDetail(User user) {
		this.userId = user.getUserId();
		this.nickName = user.getNickName();
		this.gender = user.getGender();
		this.major = user.getMajor();
		this.am = user.getAm();
		this.college = user.getCollege();
		this.enrolYear = user.getEnrolYear();
		this.profileId = user.getProfileId();
		this.wcQrcode = user.getWcQrcode();
		this.minor = user.getMinor();
		this.flag = user.getFlag();
	}
	
	public UserDetail(UserProfile userProfile) {
		this.email = userProfile.getEmail();
		//this.keyWord = userProfile.getKeyWord();
		this.qqCode = userProfile.getQqCode();
		this.phone = userProfile.getPhone();
		this.wechatId = userProfile.getWechatId();
	}
	
	public UserDetail(User user,UserProfile userProfile) {
		this.userId = user.getUserId();
		this.nickName = user.getNickName();
		this.gender = user.getGender();
		this.major = user.getMajor();
		this.am = user.getAm();
		this.college = user.getCollege();
		this.enrolYear = user.getEnrolYear();
		//this.profileId = user.getProfileId();
		this.wcQrcode = user.getWcQrcode();
		this.minor = user.getMinor();
		this.email = userProfile.getEmail();
		//this.keyWord = userProfile.getKeyWord();
		this.qqCode = userProfile.getQqCode();
		this.phone = userProfile.getPhone();
		this.wechatId = userProfile.getWechatId();
	}

	public User toUser() {
		User user = new User();
		user.setCollege(college);
		user.setEnrolYear(enrolYear);
		user.setGender(gender);
		user.setMajor(major);
		user.setAm(am);
		user.setFlag(flag);
		if (major == minor) {
			user.setMinor(null);
		} else {
			user.setMinor(minor);
		}
		user.setNickName(nickName);
		user.setUserId(userId);
		if (wcQrcode==null ||wcQrcode.length()!=20) {
			user.setWcQrcode(null);
		} else {
			user.setWcQrcode(wcQrcode);
		}
		return user;
	}

	public UserProfile toUserProfile() {
		UserProfile userPro = new UserProfile();
		userPro.setEmail(email);
		userPro.setPhone(phone);
		userPro.setProfileId(profileId);
		userPro.setQqCode(qqCode);
		userPro.setWechatId(wechatId);
		return userPro;
	}

	@Override
	public String toString() {
		StringBuffer tmp = new StringBuffer("openId:").append(openId).append(",userId:").append(userId).append(",nickName:").append(nickName).append(",wcQrcode:").append(wcQrcode).append(",email:").append(email);
		return tmp.toString();
	}


	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public Integer getEnrolYear() {
		return enrolYear;
	}

	public void setEnrolYear(Integer enrolYear) {
			this.enrolYear = enrolYear;

		
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getWcQrcode() {
		return wcQrcode;
	}

	public void setWcQrcode(String wcQrcode) {
		this.wcQrcode = wcQrcode;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getQqCode() {
		return qqCode;
	}

	public void setQqCode(String qqCode) {
		this.qqCode = qqCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
