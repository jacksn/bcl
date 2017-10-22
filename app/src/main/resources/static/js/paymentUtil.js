function filterEntries(parentName, entryName, id) {
    $('[id=' + parentName + ']').find('[id^=' + entryName + '_]').each(function () {
        if (id === 'ALL' || this.id === entryName + '_' + id) {
            this.style.display = 'block';
        } else {
            this.style.display = 'none';
        }
    });
}