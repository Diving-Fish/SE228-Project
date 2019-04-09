var booklist = new Vue({
    el: '#booklist',
    data: {
        selected_category: 0,
        sortType: 0,
        searchName: '',
        category_list: [
            { name: "全部", value: 0 },
            { name: "教材", value: 1 },
            { name: "其他书籍", value: 2 },
        ],
        books: [],
    },
    created: function() {
        let that = this;
        axios.get("http://localhost:8080/booklist/").then(function (response) {
            that.books = response.data;
        }).catch(function (error) {
            console.log(error);
        });
    },
    computed: {
        price_sortimage: function () {
            if (this.sortType === 1) {
                return 'images/up.png';
            } else if (this.sortType === 2) {
                return 'images/down.png';
            }
            return ''
        },
        stock_sortimage: function () {
            if (this.sortType === 3) {
                return 'images/up.png';
            } else if (this.sortType === 4) {
                return 'images/down.png';
            }
            return ''
        },
        filter_book: function () {
            const { books, searchName, sortType, selected_category } = this;
            arr = this.books;
            if (searchName.trim()) {
                console.log(arr);
                arr = arr.filter(p => (p.title.indexOf(searchName) !== -1) || (p.author.indexOf(searchName) !== -1));
            }
            if (selected_category) {
                arr = arr.filter(p => p.category == selected_category);
            }
            if (sortType) {
                vals = [0, 'p1.price - p2.price', 'p2.price - p1.price', 'p1.stock - p2.stock', 'p2.stock - p1.stock'];
                arr.sort(function (p1, p2) {
                    return eval(vals[sortType])
                })
            }
            return arr;
        }
    },
    methods: {
        setSortType(sortType) {
            if (this.sortType == sortType) {
                this.sortType += 1
            }
            else if (this.sortType == (sortType + 1)) {
                this.sortType = 0
            }
            else {
                this.sortType = sortType
            }
        }
    }
})