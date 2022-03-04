/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import entity.reclamation;

/**
 *
 * @author YedesHamda
 */
public interface Ireclamation <J> {

    public boolean AjouterReclam(J j);

    public boolean ModifierReclam(J j);

    public boolean SupprimerReclam(int idReclam);

    public List<reclamation> AfficherReclam(reclamation t);

}
