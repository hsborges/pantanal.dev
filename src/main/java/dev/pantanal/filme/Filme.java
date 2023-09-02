package dev.pantanal.filme;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

    @Id
    private String id;

    @Basic
    @Column
    private String titulo;

    @Basic
    @Column(columnDefinition = "integer")
    private Number duracao;

    @Basic
    @Column
    @Enumerated(EnumType.STRING)
    private Formato formato;
    
    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date lancamento;

}
