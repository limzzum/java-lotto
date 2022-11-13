package lotto.domain;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import lotto.model.Lotto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LottoMachine {
    private final int LOTTO_PRICE = 1000;
    private final int[] PRIZE = { 0, 2000000000, 30000000, 1500000, 50000, 5000};
    private int paid;
    private ArrayList<Lotto> lottos = new ArrayList<>();
    private int[] result;
    private int revenue;

    public LottoMachine() {}

    public void getInputMoney(){
        System.out.println("구입 금액을 입력해 주세요");
        int money = Integer.parseInt(Console.readLine());
        validateMoney(money);
        this.paid = Integer.parseInt(Console.readLine());
    }

    private void validateMoney(int money){
        if(money % LOTTO_PRICE != 0){
            throw new IllegalArgumentException("[ERROR] 1000원 단위로 입력해 주세요");
        }
    }

    public List<Lotto> createLottoNumber(){
        int cnt = paid / LOTTO_PRICE;
        for(int i=0; i<cnt; i++){
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            Lotto lotto = new Lotto(numbers);
            lottos.add(lotto);
        }
        return lottos;
    }
    public void printLottos(){
        for(Lotto lotto:lottos){
            System.out.println(lotto.getNumbers());
        }
    }

    public void getResult(List<Integer> winningNum, int bonusNum){
        result = new int[6];
        for(Lotto lotto:lottos){
            int rank =0;
            List<Integer> numbers = lotto.getNumbers();
            if(numbers.contains(bonusNum)) {
                rank -= 1;
            }

            numbers.retainAll(winningNum);
            rank += 8-numbers.size();
            if(numbers.size()==6){
                rank =1;
            }
            result[rank] += 1;
        }
        calculateRevenue();
    }

    public void calculateRevenue(){
        for(int i=1; i<result.length; i++){
            revenue += PRIZE[i] * result[i];
        }
    }
    public void printResult(){
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.println("3개 일치 (5,000원) - "+result[5]+"개");
        System.out.println("4개 일치 (50,000원) - "+result[4]+"개");
        System.out.println("5개 일치 (1,500,000원) - "+ result[3]+"개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - "+result[2]+"개");
        System.out.println("6개 일치 (2,000,000,000원) - "+result[1]+"개");
        System.out.println("총 수익률은 " + revenue + "%입니다.");
    }






}
