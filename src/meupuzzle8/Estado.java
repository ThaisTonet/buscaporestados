package meupuzzle8;

import java.util.Arrays;

/**
 *
 * @author Thais
 */
public class Estado {
        static final byte[] pecasFinal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        
        final byte[] pecas;
        final int spaceIndex;
        final int g;
        final int h;
        final Estado prev;

        int prioridade() {
            return g + h;
        }

        Estado(byte[] initial) {
            pecas = initial;
            spaceIndex = index(pecas, 0);
            g = 0;
            h = heuristica(pecas);
            prev = null;
        }

        Estado(Estado prev, int slideFromIndex) {
            pecas = Arrays.copyOf(prev.pecas, prev.pecas.length);
            pecas[prev.spaceIndex] = pecas[slideFromIndex];
            pecas[slideFromIndex] = 0;
            spaceIndex = slideFromIndex;
            g = prev.g + 1;
            h = heuristica(pecas);
            this.prev = prev;
        }

        boolean isGoal() {
            return Arrays.equals(pecas, pecasFinal);
        }

        Estado moverS() {
            return spaceIndex > 2 ? new Estado(this, spaceIndex - 3) : null;
        }

        Estado moverN() {
            return spaceIndex < 6 ? new Estado(this, spaceIndex + 3) : null;
        }

        Estado moverE() {
            return spaceIndex % 3 > 0 ? new Estado(this, spaceIndex - 1) : null;
        }

        Estado moverW() {
            return spaceIndex % 3 < 2 ? new Estado(this, spaceIndex + 1) : null;
        }

        void print() {
            System.out.println("p = " + prioridade() + " = g+h = " + g + "+" + h);
            for (int i = 0; i < 9; i += 3) {
                System.out.println(pecas[i] + " " + pecas[i + 1] + " " + pecas[i + 2]);
            }
        }

        void printAll() {
            if (prev != null) {
                prev.printAll();
            }
            System.out.println();
            print();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Estado) {
                Estado other = (Estado) obj;
                return Arrays.equals(pecas, other.pecas);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(pecas);
        }
        
    static int heuristica(byte[] tiles) {
        int h = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != 0) {
                h += manhattenDistance(i, tiles[i]);
            }
        }
        return h;
    }
    static int manhattenDistance(int a, int b) {
        return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
    }
    
    static int index(byte[] a, int val) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == val) {
                return i;
            }
        }
        return -1;
    }
}
