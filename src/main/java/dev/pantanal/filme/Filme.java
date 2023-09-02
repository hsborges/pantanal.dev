package dev.pantanal.filme;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Filme {

    public enum Formato {
        IMAX("IMAX"),
        _3D("3D"),
        _2D("2D");

        public final String label;

        private Formato(String label) {
            this.label = label;
        }

        @JsonCreator
        public static Formato fromString(String text) {
            for (Formato b : Formato.values()) {
                if (b.label.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }

        @JsonValue
        public String toString() {
            return this.label.toString();
        }
    }

    private String id;
    private String titulo;
    private Number duracao;
    private Formato formato;
    private Date lancamento;

}
