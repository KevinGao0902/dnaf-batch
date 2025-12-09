package com.dnaf.batch.core.facir.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * Late payment interests
 * @TableName FACIR
 */
@TableName(value ="BATCH_FACIR")
@Data
public class FACIR {
    /**
     * Invoice ID of origin
     */
    @TableId
    private Long facid;

    /**
     * Late payment batch process ID
     */
    private Long firttfid;

    /**
     * Late charge occurence number
     */
    private Integer firorder;

    /**
     * Reason for not invoicing
     */
    private String firmotif;

    /**
     * Contract currency code
     */
    private String devcode;

    /**
     * Rate code
     */
    private String taxcode;

    /**
     * Financial element ID
     */
    private Long rubid;

    /**
     * Calculation start date
     */
    private Date firdtdepart;

    /**
     * Calculation end date
     */
    private Date firdtfin;

    /**
     * Calculated Invoice ID
     */
    private Long facidcalcule;

    /**
     * Invoice line number
     */
    private Integer fliordre;

    /**
     * Originates from unpaid or settling payment
     */
    private String firorigine;

    /**
     * Lump sum
     */
    private Long firmtforfait;

    /**
     * Calculated interests amount
     */
    private Long firmtintcalcule;

    /**
     * Flag Separate Invoice
     */
    private Integer firflagfacsepare;

    /**
     * Flag Payment mode forced to cheque
     */
    private Integer firflagchq;

    /**
     * Flag Delayed calculation of invoice
     */
    private Integer firflagdiffere;

    /**
     * Flag Invoice to be sent
     */
    private Integer firflagfacture;

    /**
     * Invoiceable interests amount
     */
    private Long firmtintfacturable;

    /**
     * Lump sum amount not deducted from previous calculation
     */
    private Long firmtforfaitacquis;

    /**
     * Method ID used to calculate interests
     */
    private Long tciid;

    /**
     * Variable rate formula ID
     */
    private Long ftvid;

    /**
     * Delay threshold
     */
    private Integer firseuilretard;

    /**
     * Grace delay
     */
    private Integer firdelaitolerance;

    /**
     * Payment grace period
     */
    private Integer firdelaipmt;

    /**
     * Type of basis
     */
    private String firtypeassiette;

    /**
     * Calculation method
     */
    private String firmodecalcul;

    /**
     * Type of lump sum agreement
     */
    private String firtypeforfait;

    /**
     * Fixed percentage
     */
    private BigDecimal firpctforfait;

    /**
     * Collar rate
     */
    private Long firtxplancher;

    /**
     * Cap rate
     */
    private Long firtxplafond;

    /**
     * Count in days at numerator
     */
    private String fircomptenum;

    /**
     * Count in days at denominator
     */
    private String fircompteden;

    /**
     * Minimum amount
     */
    private Long firmtminimal;

    /**
     * Type of formula variation
     */
    private String firtypeformule;

    /**
     * Rounding up
     */
    private String firarrondi;

    /**
     * Cap Variable rate formula ID
     */
    private Long ftvidplafond;

    /**
     * Collar Variable rate formula ID
     */
    private Long ftvidplancher;

    /**
     * Invoice ID of penalty
     */
    private Long facidpenalite;

    /**
     * Penalty invoice line number
     */
    private Integer fliordrepenalite;

    /**
     * Receivable Two phase event ID
     */
    private Long sfaid;

    /**
     * Entity locking status
     */
    private String firetat;

    /**
     * Late charges calculated with contract rate
     */
    private Long firmtcontractlate;

    /**
     * Actor ID coming from ACTEUR.ACTID
     */
    private Long actidclient;

    /**
     * Amount of IOF for the instalment in default
     */
    private Long firmtlctax;

    /**
     * Maximal basis
     */
    private Long firmtmaximal;

    /**
     * Days Type
     */
    private String firdaytype;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FACIR other = (FACIR) that;
        return (this.getFacid() == null ? other.getFacid() == null : this.getFacid().equals(other.getFacid()))
            && (this.getFirttfid() == null ? other.getFirttfid() == null : this.getFirttfid().equals(other.getFirttfid()))
            && (this.getFirorder() == null ? other.getFirorder() == null : this.getFirorder().equals(other.getFirorder()))
            && (this.getFirmotif() == null ? other.getFirmotif() == null : this.getFirmotif().equals(other.getFirmotif()))
            && (this.getDevcode() == null ? other.getDevcode() == null : this.getDevcode().equals(other.getDevcode()))
            && (this.getTaxcode() == null ? other.getTaxcode() == null : this.getTaxcode().equals(other.getTaxcode()))
            && (this.getRubid() == null ? other.getRubid() == null : this.getRubid().equals(other.getRubid()))
            && (this.getFirdtdepart() == null ? other.getFirdtdepart() == null : this.getFirdtdepart().equals(other.getFirdtdepart()))
            && (this.getFirdtfin() == null ? other.getFirdtfin() == null : this.getFirdtfin().equals(other.getFirdtfin()))
            && (this.getFacidcalcule() == null ? other.getFacidcalcule() == null : this.getFacidcalcule().equals(other.getFacidcalcule()))
            && (this.getFliordre() == null ? other.getFliordre() == null : this.getFliordre().equals(other.getFliordre()))
            && (this.getFirorigine() == null ? other.getFirorigine() == null : this.getFirorigine().equals(other.getFirorigine()))
            && (this.getFirmtforfait() == null ? other.getFirmtforfait() == null : this.getFirmtforfait().equals(other.getFirmtforfait()))
            && (this.getFirmtintcalcule() == null ? other.getFirmtintcalcule() == null : this.getFirmtintcalcule().equals(other.getFirmtintcalcule()))
            && (this.getFirflagfacsepare() == null ? other.getFirflagfacsepare() == null : this.getFirflagfacsepare().equals(other.getFirflagfacsepare()))
            && (this.getFirflagchq() == null ? other.getFirflagchq() == null : this.getFirflagchq().equals(other.getFirflagchq()))
            && (this.getFirflagdiffere() == null ? other.getFirflagdiffere() == null : this.getFirflagdiffere().equals(other.getFirflagdiffere()))
            && (this.getFirflagfacture() == null ? other.getFirflagfacture() == null : this.getFirflagfacture().equals(other.getFirflagfacture()))
            && (this.getFirmtintfacturable() == null ? other.getFirmtintfacturable() == null : this.getFirmtintfacturable().equals(other.getFirmtintfacturable()))
            && (this.getFirmtforfaitacquis() == null ? other.getFirmtforfaitacquis() == null : this.getFirmtforfaitacquis().equals(other.getFirmtforfaitacquis()))
            && (this.getTciid() == null ? other.getTciid() == null : this.getTciid().equals(other.getTciid()))
            && (this.getFtvid() == null ? other.getFtvid() == null : this.getFtvid().equals(other.getFtvid()))
            && (this.getFirseuilretard() == null ? other.getFirseuilretard() == null : this.getFirseuilretard().equals(other.getFirseuilretard()))
            && (this.getFirdelaitolerance() == null ? other.getFirdelaitolerance() == null : this.getFirdelaitolerance().equals(other.getFirdelaitolerance()))
            && (this.getFirdelaipmt() == null ? other.getFirdelaipmt() == null : this.getFirdelaipmt().equals(other.getFirdelaipmt()))
            && (this.getFirtypeassiette() == null ? other.getFirtypeassiette() == null : this.getFirtypeassiette().equals(other.getFirtypeassiette()))
            && (this.getFirmodecalcul() == null ? other.getFirmodecalcul() == null : this.getFirmodecalcul().equals(other.getFirmodecalcul()))
            && (this.getFirtypeforfait() == null ? other.getFirtypeforfait() == null : this.getFirtypeforfait().equals(other.getFirtypeforfait()))
            && (this.getFirpctforfait() == null ? other.getFirpctforfait() == null : this.getFirpctforfait().equals(other.getFirpctforfait()))
            && (this.getFirtxplancher() == null ? other.getFirtxplancher() == null : this.getFirtxplancher().equals(other.getFirtxplancher()))
            && (this.getFirtxplafond() == null ? other.getFirtxplafond() == null : this.getFirtxplafond().equals(other.getFirtxplafond()))
            && (this.getFircomptenum() == null ? other.getFircomptenum() == null : this.getFircomptenum().equals(other.getFircomptenum()))
            && (this.getFircompteden() == null ? other.getFircompteden() == null : this.getFircompteden().equals(other.getFircompteden()))
            && (this.getFirmtminimal() == null ? other.getFirmtminimal() == null : this.getFirmtminimal().equals(other.getFirmtminimal()))
            && (this.getFirtypeformule() == null ? other.getFirtypeformule() == null : this.getFirtypeformule().equals(other.getFirtypeformule()))
            && (this.getFirarrondi() == null ? other.getFirarrondi() == null : this.getFirarrondi().equals(other.getFirarrondi()))
            && (this.getFtvidplafond() == null ? other.getFtvidplafond() == null : this.getFtvidplafond().equals(other.getFtvidplafond()))
            && (this.getFtvidplancher() == null ? other.getFtvidplancher() == null : this.getFtvidplancher().equals(other.getFtvidplancher()))
            && (this.getFacidpenalite() == null ? other.getFacidpenalite() == null : this.getFacidpenalite().equals(other.getFacidpenalite()))
            && (this.getFliordrepenalite() == null ? other.getFliordrepenalite() == null : this.getFliordrepenalite().equals(other.getFliordrepenalite()))
            && (this.getSfaid() == null ? other.getSfaid() == null : this.getSfaid().equals(other.getSfaid()))
            && (this.getFiretat() == null ? other.getFiretat() == null : this.getFiretat().equals(other.getFiretat()))
            && (this.getFirmtcontractlate() == null ? other.getFirmtcontractlate() == null : this.getFirmtcontractlate().equals(other.getFirmtcontractlate()))
            && (this.getActidclient() == null ? other.getActidclient() == null : this.getActidclient().equals(other.getActidclient()))
            && (this.getFirmtlctax() == null ? other.getFirmtlctax() == null : this.getFirmtlctax().equals(other.getFirmtlctax()))
            && (this.getFirmtmaximal() == null ? other.getFirmtmaximal() == null : this.getFirmtmaximal().equals(other.getFirmtmaximal()))
            && (this.getFirdaytype() == null ? other.getFirdaytype() == null : this.getFirdaytype().equals(other.getFirdaytype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFacid() == null) ? 0 : getFacid().hashCode());
        result = prime * result + ((getFirttfid() == null) ? 0 : getFirttfid().hashCode());
        result = prime * result + ((getFirorder() == null) ? 0 : getFirorder().hashCode());
        result = prime * result + ((getFirmotif() == null) ? 0 : getFirmotif().hashCode());
        result = prime * result + ((getDevcode() == null) ? 0 : getDevcode().hashCode());
        result = prime * result + ((getTaxcode() == null) ? 0 : getTaxcode().hashCode());
        result = prime * result + ((getRubid() == null) ? 0 : getRubid().hashCode());
        result = prime * result + ((getFirdtdepart() == null) ? 0 : getFirdtdepart().hashCode());
        result = prime * result + ((getFirdtfin() == null) ? 0 : getFirdtfin().hashCode());
        result = prime * result + ((getFacidcalcule() == null) ? 0 : getFacidcalcule().hashCode());
        result = prime * result + ((getFliordre() == null) ? 0 : getFliordre().hashCode());
        result = prime * result + ((getFirorigine() == null) ? 0 : getFirorigine().hashCode());
        result = prime * result + ((getFirmtforfait() == null) ? 0 : getFirmtforfait().hashCode());
        result = prime * result + ((getFirmtintcalcule() == null) ? 0 : getFirmtintcalcule().hashCode());
        result = prime * result + ((getFirflagfacsepare() == null) ? 0 : getFirflagfacsepare().hashCode());
        result = prime * result + ((getFirflagchq() == null) ? 0 : getFirflagchq().hashCode());
        result = prime * result + ((getFirflagdiffere() == null) ? 0 : getFirflagdiffere().hashCode());
        result = prime * result + ((getFirflagfacture() == null) ? 0 : getFirflagfacture().hashCode());
        result = prime * result + ((getFirmtintfacturable() == null) ? 0 : getFirmtintfacturable().hashCode());
        result = prime * result + ((getFirmtforfaitacquis() == null) ? 0 : getFirmtforfaitacquis().hashCode());
        result = prime * result + ((getTciid() == null) ? 0 : getTciid().hashCode());
        result = prime * result + ((getFtvid() == null) ? 0 : getFtvid().hashCode());
        result = prime * result + ((getFirseuilretard() == null) ? 0 : getFirseuilretard().hashCode());
        result = prime * result + ((getFirdelaitolerance() == null) ? 0 : getFirdelaitolerance().hashCode());
        result = prime * result + ((getFirdelaipmt() == null) ? 0 : getFirdelaipmt().hashCode());
        result = prime * result + ((getFirtypeassiette() == null) ? 0 : getFirtypeassiette().hashCode());
        result = prime * result + ((getFirmodecalcul() == null) ? 0 : getFirmodecalcul().hashCode());
        result = prime * result + ((getFirtypeforfait() == null) ? 0 : getFirtypeforfait().hashCode());
        result = prime * result + ((getFirpctforfait() == null) ? 0 : getFirpctforfait().hashCode());
        result = prime * result + ((getFirtxplancher() == null) ? 0 : getFirtxplancher().hashCode());
        result = prime * result + ((getFirtxplafond() == null) ? 0 : getFirtxplafond().hashCode());
        result = prime * result + ((getFircomptenum() == null) ? 0 : getFircomptenum().hashCode());
        result = prime * result + ((getFircompteden() == null) ? 0 : getFircompteden().hashCode());
        result = prime * result + ((getFirmtminimal() == null) ? 0 : getFirmtminimal().hashCode());
        result = prime * result + ((getFirtypeformule() == null) ? 0 : getFirtypeformule().hashCode());
        result = prime * result + ((getFirarrondi() == null) ? 0 : getFirarrondi().hashCode());
        result = prime * result + ((getFtvidplafond() == null) ? 0 : getFtvidplafond().hashCode());
        result = prime * result + ((getFtvidplancher() == null) ? 0 : getFtvidplancher().hashCode());
        result = prime * result + ((getFacidpenalite() == null) ? 0 : getFacidpenalite().hashCode());
        result = prime * result + ((getFliordrepenalite() == null) ? 0 : getFliordrepenalite().hashCode());
        result = prime * result + ((getSfaid() == null) ? 0 : getSfaid().hashCode());
        result = prime * result + ((getFiretat() == null) ? 0 : getFiretat().hashCode());
        result = prime * result + ((getFirmtcontractlate() == null) ? 0 : getFirmtcontractlate().hashCode());
        result = prime * result + ((getActidclient() == null) ? 0 : getActidclient().hashCode());
        result = prime * result + ((getFirmtlctax() == null) ? 0 : getFirmtlctax().hashCode());
        result = prime * result + ((getFirmtmaximal() == null) ? 0 : getFirmtmaximal().hashCode());
        result = prime * result + ((getFirdaytype() == null) ? 0 : getFirdaytype().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", facid=").append(facid);
        sb.append(", firttfid=").append(firttfid);
        sb.append(", firorder=").append(firorder);
        sb.append(", firmotif=").append(firmotif);
        sb.append(", devcode=").append(devcode);
        sb.append(", taxcode=").append(taxcode);
        sb.append(", rubid=").append(rubid);
        sb.append(", firdtdepart=").append(firdtdepart);
        sb.append(", firdtfin=").append(firdtfin);
        sb.append(", facidcalcule=").append(facidcalcule);
        sb.append(", fliordre=").append(fliordre);
        sb.append(", firorigine=").append(firorigine);
        sb.append(", firmtforfait=").append(firmtforfait);
        sb.append(", firmtintcalcule=").append(firmtintcalcule);
        sb.append(", firflagfacsepare=").append(firflagfacsepare);
        sb.append(", firflagchq=").append(firflagchq);
        sb.append(", firflagdiffere=").append(firflagdiffere);
        sb.append(", firflagfacture=").append(firflagfacture);
        sb.append(", firmtintfacturable=").append(firmtintfacturable);
        sb.append(", firmtforfaitacquis=").append(firmtforfaitacquis);
        sb.append(", tciid=").append(tciid);
        sb.append(", ftvid=").append(ftvid);
        sb.append(", firseuilretard=").append(firseuilretard);
        sb.append(", firdelaitolerance=").append(firdelaitolerance);
        sb.append(", firdelaipmt=").append(firdelaipmt);
        sb.append(", firtypeassiette=").append(firtypeassiette);
        sb.append(", firmodecalcul=").append(firmodecalcul);
        sb.append(", firtypeforfait=").append(firtypeforfait);
        sb.append(", firpctforfait=").append(firpctforfait);
        sb.append(", firtxplancher=").append(firtxplancher);
        sb.append(", firtxplafond=").append(firtxplafond);
        sb.append(", fircomptenum=").append(fircomptenum);
        sb.append(", fircompteden=").append(fircompteden);
        sb.append(", firmtminimal=").append(firmtminimal);
        sb.append(", firtypeformule=").append(firtypeformule);
        sb.append(", firarrondi=").append(firarrondi);
        sb.append(", ftvidplafond=").append(ftvidplafond);
        sb.append(", ftvidplancher=").append(ftvidplancher);
        sb.append(", facidpenalite=").append(facidpenalite);
        sb.append(", fliordrepenalite=").append(fliordrepenalite);
        sb.append(", sfaid=").append(sfaid);
        sb.append(", firetat=").append(firetat);
        sb.append(", firmtcontractlate=").append(firmtcontractlate);
        sb.append(", actidclient=").append(actidclient);
        sb.append(", firmtlctax=").append(firmtlctax);
        sb.append(", firmtmaximal=").append(firmtmaximal);
        sb.append(", firdaytype=").append(firdaytype);
        sb.append("]");
        return sb.toString();
    }
}
