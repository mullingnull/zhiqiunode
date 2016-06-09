package online.yaqian.zhiqiunode.util;

public class ValidateISBNUtil {
	public static Boolean validateISBN(String isbn13) {
		if (isbn13 == null || !isbn13.matches("^97\\d{11}")) {
			return false;
		} else {
			char []code=isbn13.toCharArray();
	         
	        int oddSum = 0;
	        int evenSum = 0;
	        for(int i=0;i<12;i++){
	            if((i+1)%2 == 1){
	                oddSum += Integer.valueOf(code[i] + "");
	            }else{
	                evenSum += Integer.valueOf(code[i] + "");
	            }
	        }
	        int digit = (10-((oddSum+evenSum*3)%10))%10;
	        if (digit == Integer.valueOf(code[12] + "")) {
				return true;
			} else {
				return false;
			}
		}
	}
}
