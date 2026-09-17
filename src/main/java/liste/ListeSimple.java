package liste;

/**
 * Liste chaînée simple (non générique) dont les éléments sont stockés dans des {@link Noeud}.
 * Les insertions se font toujours en tête ; les éléments sont comparés par référence ({@code ==}),
 * sauf dans {@link #supprimeTous(int)} et {@link #modifiePremier(Object, Object)} /
 * {@link #modifieTous(Object, Object)} où la comparaison suit le type de {@code element}.
 */
public class ListeSimple {
    private long size;
    Noeud tete;

    /**
     * @return le nombre d'éléments actuellement dans la liste
     */
    public long getSize() {
        return size;
    }

    /**
     * Ajoute un élément en tête de liste.
     *
     * @param element la valeur à insérer
     */
    public void ajout(int element) {
        tete = new Noeud(element, tete);
        size++;
    }

    /**
     * Remplace la valeur du premier nœud contenant {@code element} par {@code nouvelleValeur}.
     * Ne fait rien si l'élément n'est pas trouvé.
     *
     * @param element        la valeur recherchée
     * @param nouvelleValeur la valeur de remplacement
     */
    public void modifiePremier(Object element, Object nouvelleValeur) {
        Noeud courant = tete;
        while (courant != null && courant.getElement() != element)
            courant = courant.getSuivant();
        if (courant != null)
            courant.setElement(nouvelleValeur);
    }

    /**
     * Remplace la valeur de tous les nœuds contenant {@code element} par {@code nouvelleValeur}.
     *
     * @param element        la valeur recherchée
     * @param nouvelleValeur la valeur de remplacement
     */
    public void modifieTous(Object element, Object nouvelleValeur) {
        Noeud courant = tete;
        while (courant != null) {
            if (courant.getElement() == element)
                courant.setElement(nouvelleValeur);
            courant = courant.getSuivant();
        }
    }

    /**
     * @return une représentation textuelle de la liste, de la forme
     *         {@code "ListeSimple(Noeud(x), Noeud(y), ...)"}, ou {@code "ListeSimple()"} si elle est vide
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("ListeSimple(");
        Noeud n = tete;
        while (n != null) {
            sb.append(n);
            n = n.getSuivant();
            if (n != null)
                sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Supprime le premier nœud contenant {@code element}. Ne fait rien si la liste est vide
     * ou si l'élément n'est pas trouvé.
     *
     * @param element la valeur à supprimer
     */
    public void supprimePremier(Object element) {
        if (tete != null) {
            if (tete.getElement() == element) {
                tete = tete.getSuivant();
                size--;
                return;
            }
            Noeud precedent = tete;
            Noeud courant = tete.getSuivant();
            while (courant != null && courant.getElement() != element) {
                precedent = precedent.getSuivant();
                courant = courant.getSuivant();
            }
            if (courant != null) {
                precedent.setSuivant(courant.getSuivant());
                size--;
            }
        }
    }

    /**
     * Supprime tous les nœuds contenant {@code element}.
     *
     * @param element la valeur à supprimer
     */
    public void supprimeTous(int element) {
       tete = supprimeTousRecurs(element, tete);
    }

    /**
     * Fonction récursive auxiliaire de {@link #supprimeTous(int)} : reconstruit, à partir de
     * {@code tete}, la sous-liste débarrassée de tous les nœuds contenant {@code element}.
     *
     * @param element la valeur à supprimer
     * @param tete    la tête de la sous-liste à traiter
     * @return la nouvelle tête de la sous-liste, une fois {@code element} retiré
     */
    public Noeud supprimeTousRecurs(Object element, Noeud tete) {
        if (tete != null) {
            Noeud suiteListe = supprimeTousRecurs(element, tete.getSuivant());
            if (tete.getElement() == element) {
                size--;
                return suiteListe;
            } else {
                tete.setSuivant(suiteListe);
                return tete;
            }
        } else return null;
    }

    /**
     * @return le nœud situé juste avant le dernier de la liste, ou {@code null} si la liste
     *         est vide ou ne contient qu'un seul élément
     */
    public Noeud getAvantDernier() {
        if (tete == null || tete.getSuivant() == null)
            return null;
        else {
            Noeud courant = tete;
            Noeud suivant = courant.getSuivant();
            while (suivant.getSuivant() != null) {
                courant = suivant;
                suivant = suivant.getSuivant();
            }
            return courant;
        }
    }

    /**
     * Inverse l'ordre des nœuds de la liste, en place.
     */
    public void inverser() {
        Noeud precedent = null;
        Noeud courant = tete;
        while (courant != null) {
            Noeud next = courant.getSuivant();
            courant.setSuivant(precedent);
            precedent = courant;
            courant = next;
        }
        tete = precedent;
    }

    /**
     * Recherche le nœud précédant {@code r} dans la liste.
     *
     * @param r un nœud de la liste, différent de la tête (la liste n'est donc jamais vide
     *          puisqu'elle contient au moins {@code r})
     * @return le nœud précédant {@code r}
     */
    public Noeud getPrecedent(Noeud r) {
    // la liste n'est pas vide puisqu'on transmet un Node de la liste et le Node existe obligatoirement
        Noeud precedent = tete;
        Noeud courant = precedent.getSuivant();
        while (courant != r) {
            precedent = courant;
            courant = courant.getSuivant();
        }
        return precedent;
    }

    /**
     * Échange la position de deux nœuds de la liste. Ne fait rien si {@code r1} et {@code r2}
     * sont le même nœud.
     *
     * @param r1 le premier nœud à échanger, doit appartenir à la liste
     * @param r2 le second nœud à échanger, doit appartenir à la liste
     */
    public void echanger(Noeud r1, Noeud r2) {
        if (r1 == r2)
            return;
        Noeud precedentR1;
        Noeud precedentR2;
        if (r1 != tete && r2 != tete) {
            precedentR1 = getPrecedent(r1);
            precedentR2 = getPrecedent(r2);
            precedentR1.setSuivant(r2);
            precedentR2.setSuivant(r1);
        } else if (r1 == tete) {
            precedentR2 = getPrecedent(r2);
            precedentR2.setSuivant(tete);
            tete = r2;
        }
        else {
            precedentR1 = getPrecedent(r1);
            precedentR1.setSuivant(tete);
            tete = r1;
        }
        Noeud temp = r2.getSuivant();
        r2.setSuivant(r1.getSuivant());
        r1.setSuivant(temp);
    }

}
