function downloadPdf() {
    const element = document.querySelector('#tab-content-area');

    const qscTitle = document.getElementById('qscTitle').value;
    const qscStoreName = document.getElementById('storeName').value;

    const opt = {
        margin:       10,
        filename:     `${qscTitle}_${qscStoreName}.pdf`,
        image:        { type: 'jpeg', quality: 1 },
        html2canvas:  { scale: 2, useCORS: true, scrollY: 0 },
        jsPDF:        { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };

    html2pdf()
        .set(opt)
        .from(element)
        .save()
        .catch((error) => {
            console.error("PDF 생성 중 오류 발생:", error);
        });
}