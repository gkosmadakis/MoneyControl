/**
 * ISimpleDocumentListener.java
 * Created: 12 Nov 2020
 * Author: cousm
 */
package interfaces;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author cousm
 *
 */
@FunctionalInterface
public interface ISimpleDocumentListener extends DocumentListener {
    /**
     * @param e
     */
    void update(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}