/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database;


import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;

import javax.annotation.Generated;


/**
 * Convenience access to all sequences in procjud
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Sequences {

    /**
     * The sequence <code>procjud.t_info_distribuicao_seq_informacao_seq</code>
     */
    public static final Sequence<Long> T_INFO_DISTRIBUICAO_SEQ_INFORMACAO_SEQ = new SequenceImpl<Long>("t_info_distribuicao_seq_informacao_seq", Procjud.PROCJUD, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
