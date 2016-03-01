package meupuzzle8;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author Thais
 */
public class MeuPuzzle8 {
    public static void main(String[] args) {

        byte[] inicio = {6, 8, 0, 1, 4, 3, 2, 5, 7};

        new MeuPuzzle8().resolver(inicio);
    }
    
    final PriorityQueue<Estado> queue = new PriorityQueue<Estado>(100, new Comparator<Estado>() {
        @Override
        public int compare(Estado a, Estado b) {
            return a.prioridade() - b.prioridade();
        }
    });

    final HashSet<Estado> fechado = new HashSet<Estado>();

    void adicionarSucessor(Estado successor) {
        if (successor != null && !fechado.contains(successor)) {
            queue.add(successor);
        }
    }

    public void resolver(byte[] initial) {

        queue.clear();
        fechado.clear();

        long start = System.currentTimeMillis();

        queue.add(new Estado(initial));

        while (!queue.isEmpty()) {

            Estado estado = queue.poll();

            if (estado.isGoal()) {
                long elapsed = System.currentTimeMillis() - start;
                estado.printAll();
                System.out.println("tempo (ms) = " + elapsed);
                return;
            }

            fechado.add(estado);

            adicionarSucessor(estado.moverS());
            adicionarSucessor(estado.moverN());
            adicionarSucessor(estado.moverW());
            adicionarSucessor(estado.moverE());
        }
    }    
}
