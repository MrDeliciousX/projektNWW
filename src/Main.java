import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static java.lang.Math.sqrt;

class Plik {
    public ArrayList<Long> odczyt() throws IOException {
        Scanner scan = new Scanner(System.in);
        BufferedReader plik = null;
        String linia;
        long ilosc;
        ArrayList<Long> dane = new ArrayList<>();
        System.out.println("Podaj sciezke pliku: ");
        try {
            plik = new BufferedReader(new FileReader(scan.next()));
            linia = plik.readLine();
            ilosc = Integer.parseInt(linia);
            dane.add(ilosc);
            for (int i = 0; i < ilosc; i++) {
                linia = plik.readLine();
                long liczba = Long.parseLong(linia);
                dane.add(liczba);
            }
        } catch (Exception e) {
            System.out.println("error");
        } finally {
            if (plik != null) plik.close();
        }
        return dane;
    }
}
class Para {
    long start, end;
    public Para(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start + " " + end;
    }
}
class NWW {
    Map <Long, Para>  mapaPrzedzialow = new HashMap<>();

    long nwd(long a, long b) {
        long c;
        while (b != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    public void rozwiazanie(ArrayList<Long> dane) {
        long maxM = 1000000000000000000L;
        long maxI = 1260000;
        long end, nww, nwdd;


        for (int start = 1; start <= maxI; start++) {
            end = start + 1;
            nwdd = nwd(end, start);
            nww = (start/nwdd) * end;
            while (true) {
                end++;
                nwdd = nwd(nww, end);
                if (nww/nwdd > maxM/end) break;
                nww = (nww/nwdd) * end;
                if (!mapaPrzedzialow.containsKey(nww)) {
                    Para para = new Para(start, end);
                    mapaPrzedzialow.put(nww, para);
                } else if (mapaPrzedzialow.get(nww).start == start) {
                    Para para = new Para(start, end);
                    mapaPrzedzialow.replace(nww, para);
                }
            }
        }
        long ile_pytan = dane.get(0);
        for (int i = 1; i < ile_pytan + 1; i++) {
            long liczba = dane.get(i);
            if (mapaPrzedzialow.containsKey(liczba)) {
                System.out.println(mapaPrzedzialow.get(liczba));
            } else {
                long pierwiastek = Double.valueOf(sqrt(liczba)).longValue();
                if (pierwiastek * pierwiastek >= liczba)
                    pierwiastek--;
                if (pierwiastek * (pierwiastek + 1) == liczba)
                    System.out.println(pierwiastek + " " + (pierwiastek + 1));
                else System.out.println("NIE");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException{
        Plik plik = new Plik();
        NWW zadanie = new NWW();
        zadanie.rozwiazanie(plik.odczyt());
    }
}