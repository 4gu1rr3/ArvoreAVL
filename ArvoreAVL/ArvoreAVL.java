/**
 * Classe que implmenta um árvore de pesquisa binária AVL.
 *
 * @author Yasmin Cardozo Aguirre
 */

public class ArvoreAVL {
    /**
     * Classe que implementa o nodo da árvore.
    */
    private static final class Node {

        public Node father;
        public Node left;
        public Node right;
        public Integer element;
        private int balance; 
        
        /**
         * Método construtor da classe Node.
         * Cria um nodo sem pai, esquerda ou direita, somente adicionando o valor a ser armazenado nele.
         * @param element Elemento(Integer) a ser armazenado no nodo.
         */
        public Node(Integer element) {
            father = null;
            left = null;
            right = null;
            balance = 0;
            this.element = element;
        }
    }

    // Atributos da árvore
    private int count; //contagem do número de nodos
    private Node root; //referência para o nodo raiz

    /**
     * Método construtor da classe ArvoreAVL.
     * Cria uma árvore com count = 0 e root = null.
     */
    public ArvoreAVL() {
        count = 0;
        root = null;
    }

    /**
     * Método que esvazia a árvore.
     */
    public void clear() {
        count = 0;
        root = null;
    }

    /**
     * Método que verifica se a árvore está vazia.
     * @return Boolean "true" se a árvore esteja vazia e caso contrário "false".
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * Método que retorna a quantidade de nodos na árvore.
     * @return Integer count.
     */
    public int size() {
        return count;
    }

    /**
     * Método que retorna a raíz da árvore.
     * @return a raíz da árvore.
     */
    public Node getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root;
    }

    /**
     * Método que verifica se um elemento está ou não na árvore.
     * @param element Elemento a ser buscado.
     * @return Boolean "true" se o elemento está na árvore, caso contrário "false".
     */
    public boolean contains(Integer element) {
        Node n = searchNodeRef(element, root);
        return(n!=null);
    }

    /**
     * 
     * @param element
     * @param n
     * @return
     */
    private Node searchNodeRef(Integer element, Node n) {
        if (element == null || n == null)
            return null;
        int c = n.element.compareTo(element);
        if (c==0)
            return n;
        if (c > 0) {
            return searchNodeRef(element, n.left);
        }
        else {
            return searchNodeRef(element, n.right);
        }
    }

    /**
     * 
     * @param element
     */
    public void add(Integer element) {
        root = add(root, element, null);
        count++;
        calculaBalance(root);
        verificaBalance(root);
    }
    /**
     * 
     * @param n
     * @param e
     * @param father
     * @return
     */
    private Node add(Node n, Integer element, Node father) {
        if (n == null) { // insere
            Node aux = new Node(element);
            aux.father = father;
            return aux;
        }
        
        // Senao, insere na subarvore da esq ou da dir
        if (n.element.compareTo(element) < 0) {
            n.right = add(n.right, element, n); // dir
        }
        else {
            n.left = add(n.left, element, n); // esq
        }
        return n;
    }

    /**
     * 
     * @param element
     * @return
     */
    public boolean remove(Integer element) {
        if(element == null || root == null){
            return false;
        }
        Node n = searchNodeRef(element, root);
        if(n == null){
            return false;
        }
        remove(n);
        count --;
        return true;
    }

    private void remove(Node n) {
        Node father = n.father;
        if(n.left == null && n.right == null){ //Se N é folha
            if (father != null) {
                if(father.left == n){ //Se N é folha esquerda
                    father.left = null;
                    n.father = null;
                }else{ //Se N é folha direita
                    father.right = null;
                    n.father = null;
                }
            }else{
                root = null;
            }
        }else if(n.right != null && n.left != null){
            Node menor = smallest(n.right);
            n.element = menor.element;
            remove(menor);
        }else if(n.left == null && n.right != null){ //Se N Só tem um filho na direita
            if(father != null){
                if(father.left == n){ //Se N é filho da esquerda
                    father.left = n.right;
                }else{ //Se N é filho da direita
                    father.right = n.right;
                }
                n.right.father = father;
            }else{
                root= n.right;
                root.father = null;
            }
        }else{ //Se N Só tem um filho na esquerda
            if(father != null){
                if(father.left == n){ //Se N é filho da esquerda
                    father.left = n.left;
                }else{ //Se N é filho da direita
                    father.right = n.left;
                }
                n.right.father = father;
            }else{
                root= n.left;
                root.father = null;
            }
        }  
        verificaBalance(root);
    }

    /**
     * Retorna o menor elemento da arvore.
     * @return o menor elemento
     */
    public Integer getSmallest() {
        Node n = smallest(root);
        if (n==null)
            return null;
        else
            return n.element;
    }
    /**
     * 
     * @param n
     * @return
     */
    private Node smallest(Node n) {
        if (n == null)
            return null;
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    public int height(Node n){
        if(n.left == null && n.right == null){
            return 1;
        }else if(n.left != null && n.right == null){
            return 1 + height(n.left);
        }else if(n.left == null && n.right != null){
            return 1 + height(n.right);
        }else{
            return 1 + Math.max(height(n.left), height(n.right));
        }
    }

    public void calculaBalance(Node n){
        if(n.left == null && n.right == null){
            n.balance = 0;
        }else if(n.left == null && n.right != null){
            n.balance = height(n.right) - 0;
        }else if(n.left != null && n.right == null){
            n.balance = 0 - height(n.left);
        }else{
            n.balance = height(n.right) - height(n.left);
        }
        if(n.left != null){
            calculaBalance(n.left); 
        }
        if(n.right != null){
            calculaBalance(n.right);
        }
    }

    public Node verificaBalance(Node n){
        if(n.right != null){
            n.right= verificaBalance(n.right);
        }
        if(n.left != null){
            n.left= verificaBalance(n.left);
        }
        calculaBalance(n);
        if(n.balance >= 2 || n.balance <= -2){
            if(n.balance >= 2){
                if(n.balance * n.right.balance >0){
                    return rotacaoSimplesDireita(n);
                }else{
                    return rotacaoDuplaDireita(n);
                }
            }else{
                if(n.balance * n.left.balance > 0){
                    return rotacaoSimplesEsquerda(n);
                }else{
                    return rotacaoDuplaEsquerda(n);
                }
            }
        }
        return n;
    }

    public Node rotacaoSimplesDireita(Node n){
        Node rightChild = n.right;
        Node childChildren = null;
        if(n.right != null){
            if(n.right.left != null){
                childChildren = n.right.left;
            }
        } 
        rightChild.left = n;
        n.right = childChildren;
        if(root == n){
            root = rightChild;
        }
        return rightChild;
    }

    public Node rotacaoDuplaDireita(Node n){
        Node rightChild = n.right; //20
        Node childChildren = rightChild.left; //15
        Node insert = childChildren.right; //17

        rightChild.left = insert; //20 recebe esquerdo 17
        childChildren.right = rightChild; // 15 direito recebe 20;
        n.right = childChildren; //10 direito recebe 15

        return rotacaoSimplesDireita(n);
    }

    public Node rotacaoSimplesEsquerda(Node n){
        Node leftChild;
        Node childChildren = null;
        leftChild = n.left;
        if(n.left != null){
            if(n.left.right != null){
                childChildren = n.left.right;
            }
        }
        leftChild.right = n;
        n.left = childChildren;
        if(root == n){
            root = leftChild;
        }
        return leftChild;
    }

    public Node rotacaoDuplaEsquerda(Node n){
        Node leftChild = n.left;
        Node childChildren = leftChild.right;
        Node insert = childChildren.left;

        leftChild.right = insert;
        childChildren.left = leftChild;
        n.left = childChildren;

        return rotacaoSimplesEsquerda(n);
    }

    private void GeraConexoesDOT(Node nodo) {
        if (nodo == null) {
            return;
        }

        GeraConexoesDOT(nodo.left);
        //   "nodeA":esq -> "nodeB" [color="0.650 0.700 0.700"]
        if (nodo.left != null) {
            System.out.println("\"node" + nodo.element + "\":esq -> \"node" + nodo.left.element + "\" " + "\n");
        }

        GeraConexoesDOT(nodo.right);
        //   "nodeA":dir -> "nodeB";
        if (nodo.right != null) {
            System.out.println("\"node" + nodo.element + "\":dir -> \"node" + nodo.right.element + "\" " + "\n");
        }
        //"[label = " << nodo->hDir << "]" <<endl;
    }

    private void GeraNodosDOT(Node nodo) {
        if (nodo == null) {
            return;
        }
        GeraNodosDOT(nodo.left);
        //node10[label = "<esq> | 10 | <dir> "];
        System.out.println("node" + nodo.element + "[label = \"<esq> | " + nodo.element + " | <dir> \"]" + "\n");
        GeraNodosDOT(nodo.right);
    }

    public void GeraConexoesDOT() {
        GeraConexoesDOT(root);
    }

    public void GeraNodosDOT() {
        GeraNodosDOT(root);
    }

    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline 
    public void GeraDOT() {
        System.out.println("digraph g { \nnode [shape = record,height=.1];\n" + "\n");

        GeraNodosDOT();
        System.out.println("");
        GeraConexoesDOT(root);
        System.out.println("}" + "\n");
    } 

/**
     * 
     * @return
     */
    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPreAux(root, res);
        return res;
    }

    /**
     * 
     * @param n
     * @param res
     */
    private void positionsPreAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            res.add(n.element); //Visita o nodo
            positionsPreAux(n.left, res); //Visita a subárvore da esquerda
            positionsPreAux(n.right, res); //Visita a subárvore da direita
        }
    }

    /**
     * 
     * @return
     */
    public LinkedListOfInteger positionsPos() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPosAux(root, res);
        return res;
    }

    /**
     * 
     * @param n
     * @param res
     */
    private void positionsPosAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsPosAux(n.left, res); //Visita a subárvore da esquerda
            positionsPosAux(n.right, res); //Visita a subárvore da direita
            res.add(n.element); //Visita o nodo
        }
    }

    /**
     * 
     * @return
     */
    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }

    /**
     * 
     * @param n
     * @param res
     */
    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.left, res); //Visita a subárvore da esquerda
            res.add(n.element); //Visita o nodo
            positionsCentralAux(n.right, res); //Visita a subárvore da direita
        }
    }

    /**
     * 
     * @return
     */
    public LinkedListOfInteger positionsWidth() {
        Queue<Node> fila = new Queue<>();
        Node atual = null;
        LinkedListOfInteger res = new LinkedListOfInteger();
        if (root != null) {
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                atual = fila.dequeue();
                if (atual.left != null) {
                    fila.enqueue(atual.left);
                }
                if (atual.right != null) {
                    fila.enqueue(atual.right);
                }
                res.add(atual.element);
            }
        }
        return res;
    }
}