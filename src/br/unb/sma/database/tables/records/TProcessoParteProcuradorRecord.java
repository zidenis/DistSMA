/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables.records;


import br.unb.sma.database.tables.TProcessoParteProcurador;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * Relaciona os Procuradores das Partes interessadas em Processos Judiciais
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TProcessoParteProcuradorRecord extends UpdatableRecordImpl<TProcessoParteProcuradorRecord> implements Record3<Long, Long, Integer> {

    private static final long serialVersionUID = 1752238587;

    /**
     * Create a detached TProcessoParteProcuradorRecord
     */
    public TProcessoParteProcuradorRecord() {
        super(TProcessoParteProcurador.T_PROCESSO_PARTE_PROCURADOR);
    }

    /**
     * Create a detached, initialised TProcessoParteProcuradorRecord
     */
    public TProcessoParteProcuradorRecord(Long codProcesso, Long codParte, Integer numProcurador) {
        super(TProcessoParteProcurador.T_PROCESSO_PARTE_PROCURADOR);

        setValue(0, codProcesso);
        setValue(1, codParte);
        setValue(2, numProcurador);
    }

    /**
     * Getter for <code>procjud.t_processo_parte_procurador.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
     */
    public Long getCodProcesso() {
        return (Long) getValue(0);
    }

    /**
     * Setter for <code>procjud.t_processo_parte_procurador.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
     */
    public void setCodProcesso(Long value) {
        setValue(0, value);
    }

    /**
     * Getter for <code>procjud.t_processo_parte_procurador.cod_parte</code>. Codigo identificador da parte
     */
    public Long getCodParte() {
        return (Long) getValue(1);
    }

    /**
     * Setter for <code>procjud.t_processo_parte_procurador.cod_parte</code>. Codigo identificador da parte
     */
    public void setCodParte(Long value) {
        setValue(1, value);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>procjud.t_processo_parte_procurador.num_procurador</code>. Numero identificador do procurador
     */
    public Integer getNumProcurador() {
        return (Integer) getValue(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>procjud.t_processo_parte_procurador.num_procurador</code>. Numero identificador do procurador
     */
    public void setNumProcurador(Integer value) {
        setValue(2, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record3<Long, Long, Integer> key() {
        return (Record3) super.key();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TProcessoParteProcurador.T_PROCESSO_PARTE_PROCURADOR.COD_PROCESSO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TProcessoParteProcurador.T_PROCESSO_PARTE_PROCURADOR.COD_PARTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TProcessoParteProcurador.T_PROCESSO_PARTE_PROCURADOR.NUM_PROCURADOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getCodProcesso();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getCodParte();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getNumProcurador();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TProcessoParteProcuradorRecord value1(Long value) {
        setCodProcesso(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TProcessoParteProcuradorRecord value2(Long value) {
        setCodParte(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public TProcessoParteProcuradorRecord value3(Integer value) {
        setNumProcurador(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TProcessoParteProcuradorRecord values(Long value1, Long value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }
}
