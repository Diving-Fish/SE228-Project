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
        books: [
            { title: '深入理解计算机系统', price: 150, author: '（美）兰德尔E.布莱恩特（Randal E.Bryant）等 /2016-11-14 /机械工业出版社', picpath: 'images/srljjsjxt.jpg', link: 'book_1.html', stock: 777, category: 1 },
            { title: '数据库系统概念', price: 70, author: '(美)西尔伯沙茨 /2012-05-01 /机械工业出版社', picpath: 'images/sjkxtgn.jpg', link: 'book_2.html', stock: 888, category: 1 },
            { title: '他改变了中国', price: 46.6, author: '[美]罗伯特·劳伦斯·库恩 著，谈峥 于海江 等译，陆谷孙 校 /2005-01-01 /上海译文出版社', picpath: 'images/tgblzg.jpg', link: 'book_3.html', stock: 800, category: 2 },
        ],
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
            let arr = [...books];

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