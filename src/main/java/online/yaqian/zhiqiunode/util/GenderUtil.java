package online.yaqian.zhiqiunode.util;

public class GenderUtil {
	public static final String getGender2Full(int gender){
		switch (gender) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "未知";
		}
	}
	
	
	
	
	public static final int getGender2Short(String gender){
		switch (gender) {
		case "男":
			return 1;
		case "女":
			return 2;
		default:
			return 0;
		}
	}
}
