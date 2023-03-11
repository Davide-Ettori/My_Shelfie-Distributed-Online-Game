package playground.junit;

public class AdderWithCap { // questa classe rappresenta un semplicissimo adder, ovvero somma 2 numeri
    // i 2 numeri da sommare devono essere entrambi POSITIVI
    // se il risultato della somma va oltre il cap tale risultato viene settato a -1
    // il cap può essere cambiato e letto con i getter e i setter appropriati
    private int cap;
    public AdderWithCap(int cap){
        this.cap = cap;
    }
    public int getCap(){
        return this.cap;
    }
    public void setCap(int cap){
        this.cap = cap;
    }
    public int add(int a, int b) throws IllegalArgumentException{
        if(a < 0 || b < 0){
            throw new IllegalArgumentException();
        }
        if(a + b <= cap){
            return a + b;
        }
        return -1;
    }
}


// vai nella cartella apposita 'test' per vedere la batteria di test che verifica il funzionamento di questa classe
// oppure puoi lanciare i test direttamente con Maven, in alto a destra