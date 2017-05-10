package command.user_interface_command;

import command.AbstractCommand;
import command.Command;

import java.util.Scanner;

/**
 * Created by mustafa on 10.05.2017.
 */
public class AnswerCommand extends AbstractCommand implements Command {
    public boolean execute(String[] parameter) {
        if(!validateParameter(parameter)) {
            return false;
        }

        Scanner in = new Scanner(System.in);
        System.out.print("answer=>");
        String sentence = in.nextLine();

        while(!sentence.equals("change")) {
            // burda cevap ara !!!
            /*
                soru kontrolü
                en az 2 kelime
                soru kelimelerini içeren bir listen olsun
                soru kelimesi kullanmak zorunda
                soru türkçe olmak zorunda
                sorunun neden geçersiz olduğunu söylemeslisin
             */

            /*
                benzerlik thrsholdun altındaysa bildir
             */
            /*
                parametreler
                threshold yüzde değeri
                gösterilecek max cevap sayısı
                kullanılacak benzerlik algoritması
                w2v ye çevrilme tipi (near, average) kelime nasıl alınacak
             */
            /*
                Kullanıcı sorusunun ve cevap cümlelerinin w2v ye çevrilmesi gerek ya hani
                sentence ile başlayan komutta ki çevirme işlemlerini ayrı bir sınıfa al ordan erişs
             */
            /*
                önce tüm soru cümlelerinden benzerlik ara ama ileride
                ilgili dokümanların getirilmesi gerek ona bir information retrieval ayarı gerek
             */
            System.out.print("answer=>");
            sentence = in.nextLine();
        }

        return true;
    }

    protected boolean validateParameter(String[] parameter) {
        return parameter.length == 1;
    }
}
