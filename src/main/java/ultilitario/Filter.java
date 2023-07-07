/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultilitario;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import java.io.IOException;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Produto;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import servicos.ServicoProduto;

/**
 *
 * @author paulo
 */
@Named
@ViewScoped
public class Filter implements Serializable {

    @EJB
    private ServicoProduto servico;
    private boolean globalFilter;
    private List<Produto> produtosFiltrados;
    private List<Produto> produtos;
    private List<String> columns;
    private List<String> selectedColumns;
    private List<Boolean> list;

    @PostConstruct
    public void inti() {
        produtos = servico.findAll();
        produtosFiltrados = new ArrayList<>();
        columns = new ArrayList<>();
        columns.add("nome");
        columns.add("lote");
        columns.add("quantidade");
        columns.add("preco");
        columns.add("validade");
        list = new ArrayList<>();
        selectedColumns = new ArrayList<>(columns);
        colunasVisiveis();
    }

    public void colunasVisiveis() {
        for (int i = 0; i < columns.size(); i++) {
            list.add(i, true);
        }
    }

    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    private int[] getSelectedColumnsIndexes() {
        int[] selectedIndexes = new int[selectedColumns.size()];
        List<String> columns = getColumns();
        for (int i = 0; i < selectedColumns.size(); i++) {
            selectedIndexes[i] = columns.indexOf(selectedColumns.get(i));
        }
        return selectedIndexes;
    }

    public void preProcessExport(Object document) throws IOException {
        if (document instanceof PdfDocument) {
            PdfDocument pdfDocument = (PdfDocument) document;
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document doc = new Document(pdfDocument, PageSize.A4);
            PdfFont font = PdfFontFactory.createFont();

            Table table = new Table(5);
            table.setWidth(UnitValue.createPercentValue(100));

            for (int i = 0; i < 10; i++) {
                table.addCell("teste");
            }
            doc.add(table);
            doc.close();
        }
    }

    public List<Boolean> getList() {
        return list;
    }

    public void setList(List<Boolean> list) {
        this.list = list;
    }

    public List<String> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<Produto> getProdutosFiltrados() {
        return produtosFiltrados;
    }

    public void setProdutosFiltrados(List<Produto> produtosFiltrados) {
        this.produtosFiltrados = produtosFiltrados;
    }

    public boolean isGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(boolean globalFilter) {
        this.globalFilter = globalFilter;
    }

    public ServicoProduto getServico() {
        return servico;
    }

    public void setServico(ServicoProduto servico) {
        this.servico = servico;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
