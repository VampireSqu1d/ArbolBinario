import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static class Nodo{
        int valor;
        Nodo izq;
        Nodo der;
        public Nodo(int valor){
            this.valor = valor;
            this.izq = null;
            this.der = null;
        }
    }

    public  static Nodo CargaArchivo(String archivo){
        Nodo raiz = null;
        try {
            Scanner sc = new Scanner(new File(archivo));
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                int numero = Integer.parseInt(linea);
               raiz = Inserta(raiz,numero);
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return raiz;
    }

    public static Nodo Inserta(Nodo raiz,int numero){
        if (raiz == null){
            raiz = new Nodo(numero);
        } else{
            if ( numero < raiz.valor){
                raiz.izq = Inserta(raiz.izq, numero);
            } else{
                raiz.der = Inserta(raiz.der,numero);
            }
        }
        return  raiz;
    }

    public static void PreOrder(Nodo n){
        if (n != null){
            Despliega(n);
            PostOrder(n.izq);
            PostOrder(n.der);
        }
    }

    public static void PostOrder(Nodo n){
        if (n != null){
            PreOrder(n.izq);
            Despliega(n);
            PreOrder(n.der);
        }
    }

    public  static void Ascendente(Nodo n){
        if (n != null){
            Ascendente(n.der);
            Despliega(n);
            Ascendente(n.izq);
        }
    }

    public static LinkedList<Nodo> InOrder(Nodo n){
        Queue<Nodo> nodos = new LinkedList<>();
        LinkedList<Nodo> resultados = new LinkedList<>();
        nodos.add(n);
        while (nodos.size() != 0){
                Nodo actual = nodos.poll();
            if (actual != null){
                resultados.add(actual);
                if (actual.der != null) nodos.add(actual.der);

                if ( actual.izq != null) nodos.add(actual.izq);

            } else if (actual == null){
                resultados.add(actual);
            }

        }
        return resultados;
    }

    public static void EnOrden(Nodo n){
        if (n != null){
            EnOrden(n.izq);
            System.out.println(n.valor);
            EnOrden(n.der);
        }
    }

    public static void Existe(LinkedList<Nodo> lista,String numero){
        boolean existe = false;
        int number = Integer.parseInt(numero);

        for (Nodo n : lista){
            if (n.valor == number) existe = true;
        }

        if (existe) {
            System.out.println("si existe el numero en el arbol :)");
        } else {
            System.out.println("El numero no esta en el arbol");
        }

    }

    public static Stack<String> GeneraInOrder(Nodo n, Stack<String> s){
        if (n != null){
            GeneraInOrder(n.izq, s);

            if (n.izq != null) s.push(String.valueOf(n.valor) + "--" + String.valueOf(n.izq.valor));

            if (n.der != null) s.push(String.valueOf(n.valor) + "--" + String.valueOf(n.der.valor));

            GeneraInOrder(n.der, s);
        }

        return s;
    }

    public static void Despliega(Nodo n){
        System.out.println(n.valor);
    }

    public static void Imprimir(Stack<String> s) throws FileNotFoundException {
        File salida = new File("salida.txt");
        PrintStream ps = new PrintStream(salida);
        Stack<String> nuevoStack = new Stack<>();
        int j = s.size();
        for (int i =0; i < j; i++){
            String re = s.pop();
            nuevoStack.push(re);
        }

            while (nuevoStack.empty() != true) ps.println(nuevoStack.pop());

        ps.flush();
        ps.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length >= 1){
            Nodo raiz = CargaArchivo(args[0]);
            //System.out.println("====SIN ORDEN====");
            //PreOrder(raiz);
            //System.out.println("====IZQ PRIMERO====");
            //PostOrder(raiz);
            System.out.println("====DE MAYOR A MENOR====");
            Ascendente(raiz);
            //System.out.println("====EN ORDEN(creo)====");

            if (args.length >= 2){
                LinkedList<Nodo> enes =  InOrder(raiz);
                for (Nodo n: enes) System.out.println(n.valor);
                //Existe(enes, args[1]);


                Stack<String> strings = new Stack<>();
                GeneraInOrder(raiz, strings);

                Imprimir(strings);
            }


            //Imprimir(raiz);
        }


    }
}
