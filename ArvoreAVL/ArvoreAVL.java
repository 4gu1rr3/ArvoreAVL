/**
 * Classe que implmenta um árvore de pesquisa binária AVL.
 *
 * @author Yasmin Cardozo Aguirre - 23111329
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
     * Método que retorna o nodo pai de um determinado nodo.
     * @param n Nodo filho.
     * @return Nodo pai.
     */
    public Node getParent(Node n){
        return n.father;
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
     * Método que busca um determinado nodo.
     * @param element Elemento a ser buscado.
     * @param n Nodo para comparar com o elemento.
     * @return Nodo com o elemento.
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
     * Método que adiciona um elemento na árvore.
     * @param element Elemento a ser adicionado.
     */
    public void add(Integer element) {
        root = add(root, element, null);
        count++;
        calculaBalance(root);
        verificaBalance(root);
    }
    /**
     * Método que faz a inserção de um novo elemento na árvore de modo recursivo.
     * Se nodo n = null chegamos no final da árvore, logo father recebe e como filho.
     * Notação O: O(log(n)).
     * @param n Nodo utilizado para verificar se já chegamos no fim da árvore.
     * @param e Elemento a ser adicionado.
     * @param father Nodo pai do nodo n.
     * @return
     */
    private Node add(Node n, Integer element, Node father) { //Notação O: O(log(n)).
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
     * Busca o menor elemento da árvore.
     * @param n Nodo raiz.
     * @return Nodo com o menor valor.
     */
    private Node smallest(Node n) {
        if (n == null)
            return null;
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

    /**
     * Método que retorna a altura da árvore. Notação O: O(n).
     * @param n Nodo raiz.
     * @return Altura da árvore.
     */
    public int height(Node n){ //Notação O: O(n).
        if(n.left == null && n.right == null){
            return 0;
        }else if(n.left != null && n.right == null){
            return 1 + height(n.left);
        }else if(n.left == null && n.right != null){
            return 1 + height(n.right);
        }else{
            return 1 + Math.max(height(n.left), height(n.right));
        }
    }

    /**
     * Método que calcula o balancemento dos nodos e atualiza o atributo balance deles.
     * @param n nodo a ser calculado o balancemaneto.
     */
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

    /**
     * Método que verifica se a árvore está balanceada.
     * @param n Nodo raiz.
     * @return Nodo balanceado.
     */
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

    /**
     * Método que faz a rotação simples a direita dos nodos.
     * @param n Nodo desbalanceado.
     * @return Antigo filho direito do nodo n.
     */
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
    
    /**
     * Método que faz a rotação dupla a direita. Alinha os nodos e chama a rotação simples a direita.
     * @param n Nodo desbalanceado.
     * @return Antigo filho direito do nodo n.
     */
    public Node rotacaoDuplaDireita(Node n){
        Node rightChild = n.right; 
        Node childChildren = rightChild.left; 
        Node insert = childChildren.right; 

        rightChild.left = insert; 
        childChildren.right = rightChild;
        n.right = childChildren;

        return rotacaoSimplesDireita(n);
    }

    /**
     * Método que faz a rotação simples a esquerda dos nodos.
     * @param n Nodo desbalanceado.
     * @return Antigo filho esquerdo do nodo n.
     */
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

    /**
     * Método que faz a rotação dupla a esquerda. Alinha os nodos e chama a rotação simples a esquerda.
     * @param n Nodo desbalanceado.
     * @return Antigo filho esquerdo do nodo n.
     */
    public Node rotacaoDuplaEsquerda(Node n){
        Node leftChild = n.left;
        Node childChildren = leftChild.right;
        Node insert = childChildren.left;

        leftChild.right = insert;
        childChildren.left = leftChild;
        n.left = childChildren;

        return rotacaoSimplesEsquerda(n);
    }


    /**
     * Método que retorna uma lista encadeada com os elementos da árvore na ordem do caminhamento central.
     * @return Lista encadeada.
     */
    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }

    /**
     * Método que monta a lista encadeada com os elementos da árvore na ordem do caminhamento central.
     * @param n Nodo.
     * @param res Lista.
     */
    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.left, res); //Visita a subárvore da esquerda
            res.add(n.element); //Visita o nodo
            positionsCentralAux(n.right, res); //Visita a subárvore da direita
        }
    }

    /**
     * Método que retorna uma lista encadada com os elementos da arvore na ordem do caminhamento por largura.
     * @return lista encadeada.
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
}