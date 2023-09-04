package dev.pantanal.filme;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

        public static Formato fromString(String text) {
            for (Formato b : Formato.values()) {
                if (b.label.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }

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
    @Column
    private Integer duracao;

    @Basic
    @Column
    private Integer classificacao;

    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Byte[] imagem;

    @Column
    @Enumerated(EnumType.STRING)
    private Formato formato;

    @Basic
    @Column
    @Temporal(TemporalType.DATE)
    private Date lancamento;

}
