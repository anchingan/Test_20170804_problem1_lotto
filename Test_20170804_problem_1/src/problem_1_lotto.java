/*
 * Test 20170804 第一次評測上機考
 * Problem_1 模擬樂透中獎
 * 訂正版
 */

import java.util.Scanner;

public class problem_1_lotto {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("持有資金：");
		int cashOfPlayer = scanner.nextInt();
		
		System.out.print("樂透彩一注下注金額：");
		int price = scanner.nextInt();
		
		System.out.print("累積獎金：");
		int totalPrizeIni = scanner.nextInt();
		int totalPrize = totalPrizeIni;
		
		int input, status = 0, amount = 0;
		int[] prize = {0, 0, 0, 0};
		do {
			switch (status) {
			case 0: //初始畫面，詢問使用者要下注或終止。
				System.out.print("1) 簽注 2) 終止:");
				input = scanner.nextInt();
				
				if (input == 1)
					status = 1;
				else if (input == 2)
					status = -1;
				else
					System.out.println("輸入錯誤！");
				break;
				
			case 1: //決定購買張數。
				System.out.print("購買張數：");
				amount = scanner.nextInt();
				if (amount * price > cashOfPlayer)
					System.out.println("現金不足！");
				else {
					System.out.printf("您購買了%d張樂透。", amount);
					cashOfPlayer -= amount * price;
					status = 2;
				}
				break;
				
			case 2: //開出樂透號碼及下注號碼。
				int[] lottoNumbers = doLotto();
				System.out.print("\n本期開出號碼：");
				for (int i = 0; i < 5; i++)
					System.out.printf("%2d ", lottoNumbers[i]);
				System.out.print("\n");
				
				int prizeType;
				int[] myNumbers = new int [5];
				for (int i = 0; i < amount; i++) {
					myNumbers = doLotto();
					prizeType = match(myNumbers, lottoNumbers);
					System.out.printf("您簽注的第%d組號碼為： ", (i + 1));
					System.out.printf("您簽注的號碼為： ");
					printLottoNumbers(myNumbers, prizeType);
					System.out.print("\n");
					prize[prizeType]++;
				}
				status = 3;
				break;
				
			case 3: //計算獎金。
				int totalPrizeGet = 0;
				//若有得頭獎，得到全部獎金。(無論得到幾次頭獎)
				if (prize[1] != 0)
					totalPrizeGet += totalPrize;
				totalPrizeGet += (int)(prize[2] * 0.2 * totalPrize);
				totalPrizeGet += (int)(prize[3] * 0.01 * totalPrize);
				cashOfPlayer += totalPrizeGet;
				totalPrize -= totalPrizeGet;
				System.out.printf("您總共得到%d元！\n持有資金剩餘%d元。", totalPrizeGet, cashOfPlayer);
				if (cashOfPlayer <= 0) {
					System.out.println("\n資金不足！");
					status = -1;
					break;
				}
				if (totalPrize <= 0)
					totalPrize = totalPrizeIni;
				else
					totalPrize *= 1.06;
				status = 0;
				break;
			}
		} while (status != -1);
		
		System.out.print("\nProgram terminate.");

	}
	
	public static int[] doLotto() {
		int check; 
		int[] numbers = new int [5];
		int count = 0, num;
		while (count < 5) {
			num = (int)(Math.random() * 32 + 1); 
			if (count != 0) {
				check = isDuplicated(num, numbers);
				if (check == 1)
					;
				else
					numbers[count++] = num;
			}
			else
				numbers[count++] = num;
		}
		return numbers;
	}
	
	public static int match(int[] myNumbers, int[] lottoNumbers) {
		int prizeType = 0;
		if (myNumbers[4] == lottoNumbers[4]) {
			if (myNumbers[3] == lottoNumbers[3]) {
				if (myNumbers[2] == lottoNumbers[2]) {
					if (myNumbers[1] == lottoNumbers[1]) {
						if (myNumbers[0] == lottoNumbers[0])
							prizeType = 1;
						else
							prizeType = 2;
					}
					else
						prizeType = 3;
				}
			}
		}
		return prizeType;
	}
	
	public static int isDuplicated(int key, int[] s) {
		int found = 0;
		for (int i = 0; i < s.length; i++) {
			if (key == s[i])
				found = 1;
		}
		return found;
	}
	
	public static void printLottoNumbers(int[] printNumbers, int prizeType) {
		for (int i = 0; i < printNumbers.length; i++) {
			System.out.printf("%2d ", printNumbers[i]);
		}
		String[] prizeName= {"槓龜","頭獎","貳獎","參獎"};
		if (prizeType == 0)
			System.out.printf("......%s", prizeName[prizeType]);
		else 
			System.out.printf("......恭喜您！中%s", prizeName[prizeType]);
	}

}
