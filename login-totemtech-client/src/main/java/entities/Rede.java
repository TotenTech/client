package entities;

import java.sql.Timestamp;

public class Rede {

    /*
    idredeRegistro INT auto_increment,
  download DOUBLE,
  upload DOUBLE,
  horario DATETIME,
  totem INT,
     */
    private Integer idrede;
    private Double download;
    private Double upload;
    private Timestamp horario;
    private Integer totem;

    public Rede() {}

    public Rede(
            Double download,
            Double upload,
            Timestamp horario,
            Integer totem
    ) {
        this.download = download;
        this.upload = upload;
        this.horario = horario;
        this.totem = totem;
    }

    public Rede(Integer idrede, Double download, Double upload, Timestamp horario, Integer hardware, Integer totem) {
        this.idrede = idrede;
        this.download = download;
        this.upload = upload;
        this.horario = horario;
        this.totem = totem;
    }

    public Integer getIdRede() {
        return idrede;
    }

    public void setIdRede(Integer idRede) {
        this.idrede = idRede;
    }

    public Double getDownload() {
        return download;
    }

    public void setDownload(Double download) {
        this.download = download;
    }

    public Double getUpload() {
        return upload;
    }

    public void setUpload(Double upload) {
        this.upload = upload;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }
}
