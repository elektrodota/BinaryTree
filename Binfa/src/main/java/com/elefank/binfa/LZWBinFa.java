package com.elefank.binfa;
public class LZWBinFa {
    public LZWBinFa() {

        fa = gyoker;

    }
    public void egyBitFeldolg(char b) {
        if (b == '0') {
            if (fa.nullasGyermek() == null) // ha nincs, hÃ¡t akkor csinÃ¡lunk
            {
                Csomopont uj = new Csomopont('0');
                fa.ujNullasGyermek(uj);
                fa = gyoker;
            } else
            {

                fa = fa.nullasGyermek();
            }
        } else {
            if (fa.egyesGyermek() == null) {
                Csomopont uj = new Csomopont('1');
                fa.ujEgyesGyermek(uj);
                fa = gyoker;
            } else {
                fa = fa.egyesGyermek();
            }
        }
    }
    public void kiir() {
        kiir(gyoker, new java.io.PrintWriter(System.out));

    }
    public void kiir(java.io.PrintWriter os) {
        melyseg = 0;
        kiir(gyoker, os);
    }
    class Csomopont {
        public Csomopont(char betu) {
            this.betu = betu;
            balNulla = null;
            jobbEgy = null;
        };
        public Csomopont nullasGyermek() {
            return balNulla;
        }
        public Csomopont egyesGyermek() {
            return jobbEgy;
        }
        public void ujNullasGyermek(Csomopont gy) {
            balNulla = gy;
        }
        public void ujEgyesGyermek(Csomopont gy) {
            jobbEgy = gy;
        }
        public char getBetu() {
            return betu;
        }
        private char betu;
        private Csomopont balNulla = null;
        private Csomopont jobbEgy = null;
    };
    private Csomopont fa = null;

    private int melyseg, atlagosszeg, atlagdb;
    private double szorasosszeg;



    public void kiir(Csomopont elem, java.io.PrintWriter os) {
        if (elem != null) {
            ++melyseg;
            kiir(elem.egyesGyermek(), os);
            for (int i = 0; i < melyseg; ++i) {
                os.print("---");
            }
            os.print(elem.getBetu());
            os.print("(");
            os.print(melyseg - 1);
            os.print(")<br/>");
            kiir(elem.nullasGyermek(), os);
            --melyseg;
        }
    }
    protected Csomopont gyoker = new Csomopont('/');
    int maxMelyseg;
    double atlag, szoras;
    public int getMelyseg() {
        melyseg = maxMelyseg = 0;
        rmelyseg(gyoker);
        return maxMelyseg - 1;
    }

    public double getAtlag() {
        melyseg = atlagosszeg = atlagdb = 0;
        ratlag(gyoker);
        atlag = ((double) atlagosszeg) / atlagdb;
        return atlag;
    }

    public double getSzoras() {
        atlag = getAtlag();
        szorasosszeg = 0.0;
        melyseg = atlagdb = 0;

        rszoras(gyoker);

        if (atlagdb - 1 > 0) {
            szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
        } else {
            szoras = Math.sqrt(szorasosszeg);
        }

        return szoras;
    }

    public void rmelyseg(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            if (melyseg > maxMelyseg) {
                maxMelyseg = melyseg;
            }
            rmelyseg(elem.egyesGyermek());
            rmelyseg(elem.nullasGyermek());
            --melyseg;
        }
    }

    public void ratlag(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            ratlag(elem.egyesGyermek());
            ratlag(elem.nullasGyermek());
            --melyseg;
            if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
                ++atlagdb;
                atlagosszeg += melyseg;
            }
        }
    }

    public void rszoras(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            rszoras(elem.egyesGyermek());
            rszoras(elem.nullasGyermek());
            --melyseg;
            if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
                ++atlagdb;
                szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
            }
        }
    }





}
