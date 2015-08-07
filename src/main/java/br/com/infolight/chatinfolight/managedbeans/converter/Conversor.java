package br.com.infolight.chatinfolight.managedbeans.converter;

import br.com.infolight.chatinfolight.entidades.Identificavel;
import br.com.infolight.chatinfolight.persistencia.Dao;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author filipe
 */
public abstract class Conversor<T> implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equals("")) {
            return null;
        }

        T identificavel = null;

        try {
            identificavel = getDao().buscaPorId(Long.parseLong(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (identificavel == null) {
            throw new ConverterException(new FacesMessage("Identificação desconhecida:  " + value));
        }

        return identificavel;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            Identificavel i = (Identificavel) value;
            return String.valueOf(i.getIdentificador());
        }
    }

    public abstract Dao<T> getDao();

}
