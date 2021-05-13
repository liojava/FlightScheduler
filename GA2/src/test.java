import java.util.Random;

public class test {

	public static void main(String[] args) {
		Random rand = new Random();
		char a = 'a';
		String str = "";
		String total = "";
		for(int i = 0; i < 8; i++) {
			int randomInt = rand.nextInt(26);
			int randomInt1 = rand.nextInt(2) + 1;
			a = (char) (a + randomInt);
			str = Character.toString(a);
			if(randomInt1 == 1) {
				str = str.toUpperCase();		
			}
			else if(randomInt1 == 2) {
				str = str.toLowerCase();
			}
			total = total + str;
			a = 'a';
		}
		System.out.println(total);
	}

}
