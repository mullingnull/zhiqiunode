package online.yaqian.zhiqiunode.util;


public class CollegeUtil {
	
	public static final boolean validateShortCollegeForm(String shortForm) {
		if (getCollege2Full(shortForm).equals("其它学院")) {
			return false;
		}
		return true;
	}
	
	public static final String getCollege2Full(String college) {
		switch (college) {
		case "a":
			return "机械与自动控制学院";
		case "b":
			return "文化传播学院";
		case "c":
			return "材料与纺织学院";
		case "f":
			return "法政学院";
		case "g":
			return "经济管理学院";
		case "j":
			return "建筑工程学院";
		case "l":
			return "理学院";
		case "q":
			return "启新学院";
		case "s":
			return "生命科学学院";
		case "x":
			return "信息学院";
		case "w":
			return "外国语学院";
		case "y":
			return "艺术与设计学院";
		case "z":
			return "服装学院";
		default:
			return "其它学院";
		}
	}
	
	public static final String getCollege2Short(String college) {
		switch (college) {
		case "机械与自动控制学院":
			return "a";
		case "文化传播学院":
			return "b";
		case "材料与纺织学院":
			return "c";
		case "法政学院":
			return "f";
		case "经济管理学院":
			return "g";
		case "建筑工程学院":
			return "j";
		case "理学院":
			return "l";
		case "启新学院":
			return "q";
		case "生命科学学院":
			return "s";
		case "外国语学院":
			return "w";
		case "信息学院":
			return "x";
		case "艺术与设计学院":
			return "y";
		case "服装学院":
			return "z";
		default:
			return "p";
		}
	}
	
}
