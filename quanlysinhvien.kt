data class Student(
    val id: String,
    val fullName: String,
    val age: Int,
    val major: String,
    val gpa: Double
)

class StudentManager {
    private val students = mutableListOf<Student>()

    fun addStudent(s: Student) {
        students.add(s)
        println("Đã thêm sinh viên: ${s.fullName}")
    }

    fun displayAll() {
        printStudentList(students)
    }

    fun searchById(id: String): Student? {
        for (s in students) {
            if (s.id == id) return s
        }
        return null
    }

    fun searchByName(keyword: String): List<Student> {
        val result = mutableListOf<Student>()
        val kw = keyword.lowercase()
        for (s in students) {
            if (s.fullName.lowercase().contains(kw)) {
                result.add(s)
            }
        }
        return result
    }

    fun removeStudent(id: String): Boolean {
        var index = -1
        for (i in students.indices) {
            if (students[i].id == id) {
                index = i
                break
            }
        }
        if (index == -1) return false
        students.removeAt(index)
        return true
    }

    fun countGpaAbove8(): Int {
        var count = 0
        for (s in students) {
            if (s.gpa >= 8.0) count++
        }
        return count
    }

    fun countGpaBelow5(): Int {
        var count = 0
        for (s in students) {
            if (s.gpa < 5.0) count++
        }
        return count
    }

    fun averageGpaByMajor(major: String): Double {
        var total = 0.0
        var count = 0
        for (s in students) {
            if (s.major.equals(major, ignoreCase = true)) {
                total += s.gpa
                count++
            }
        }
        if (count == 0) return 0.0
        val avg = total / count
        val rounded = Math.round(avg * 100).toDouble() / 100
        return rounded
    }

    fun highestGpaStudent(): Student? {
        if (students.isEmpty()) return null
        var best = students[0]
        for (s in students) {
            if (s.gpa > best.gpa) best = s
        }
        return best
    }

    fun oldestStudent(): Student? {
        if (students.isEmpty()) return null
        var oldest = students[0]
        for (s in students) {
            if (s.age > oldest.age) oldest = s
        }
        return oldest
    }

    fun studentsInGpaRange(min: Double = 7.0, max: Double = 8.5): List<Student> {
        val result = mutableListOf<Student>()
        for (s in students) {
            if (s.gpa >= min && s.gpa <= max) result.add(s)
        }
        return result
    }

    fun studentsByMajor(major: String): List<Student> {
        val result = mutableListOf<Student>()
        for (s in students) {
            if (s.major.equals(major, ignoreCase = true)) result.add(s)
        }
        return result
    }

    fun sortByGpaDescending(): List<Student> {
        val list = students.toMutableList()
        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j].gpa < list[j + 1].gpa) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    fun top3ByGpa(): List<Student> {
        val sorted = sortByGpaDescending()
        val result = mutableListOf<Student>()
        var i = 0
        while (i < sorted.size && i < 3) {
            result.add(sorted[i])
            i++
        }
        return result
    }

    fun sortByAge(): List<Student> {
        val list = students.toMutableList()
        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j].age > list[j + 1].age) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    fun sortByName(): List<Student> {
        return sortByNameAZ()
    }

    fun sortByNameAZ(): List<Student> {
        val list = students.toMutableList()
        val n = list.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (list[j].fullName.lowercase() > list[j + 1].fullName.lowercase()) {
                    val temp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = temp
                }
            }
        }
        return list
    }

    fun countPassFail(): Pair<Int, Int> {
        var pass = 0
        var fail = 0
        for (s in students) {
            if (s.gpa >= 4.0) pass++ else fail++
        }
        return Pair(pass, fail)
    }

    fun loadSampleData() {
        addStudent(Student("SV001", "Nguyen Van An", 20, "Công nghệ thông tin", 8.5))
        addStudent(Student("SV002", "Tran Thi Bich", 21, "Kinh tế", 6.8))
        addStudent(Student("SV003", "Le Van Cuong", 22, "Công nghệ thông tin", 4.5))
        addStudent(Student("SV004", "Pham Thi Dung", 19, "Kế toán", 7.9))
        addStudent(Student("SV005", "Hoang Van Em", 23, "Công nghệ thông tin", 9.2))
    }
}

fun printStudentList(list: List<Student>) {
    if (list.isEmpty()) {
        println("Không tìm thấy sinh viên nào.")
        return
    }
    println("%-8s %-20s %-5s %-15s %-5s".format("ID", "Họ tên", "Tuổi", "Ngành", "GPA"))
    for (s in list) {
        println("%-8s %-20s %-5d %-15s %-5.2f".format(s.id, s.fullName, s.age, s.major, s.gpa))
    }
}

fun readInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val input = readLine()
        val value = input?.toIntOrNull()
        if (value != null) return value
        println("Giá trị không hợp lệ, vui lòng nhập lại.")
    }
}

fun readDouble(prompt: String): Double {
    while (true) {
        print(prompt)
        val input = readLine()
        val value = input?.toDoubleOrNull()
        if (value != null) return value
        println("Giá trị không hợp lệ, vui lòng nhập lại.")
    }
}

fun readGpa(prompt: String): Double {
    while (true) {
        print(prompt)
        val input = readLine()
        val value = input?.toDoubleOrNull()
        if (value == null) {
            println("Giá trị không hợp lệ, vui lòng nhập lại.")
        } else if (value < 0.0 || value > 10.0) {
            println("GPA phải nằm trong khoảng 0 -> 10, vui lòng nhập lại.")
        } else {
            return value
        }
    }
}

fun main() {
    val manager = StudentManager()
    manager.loadSampleData()

    while (true) {
        println("\n========== STUDENT MANAGEMENT ==========")
        println("1. Add student")
        println("2. Display all students")
        println("3. Search student")
        println("4. Calculate average GPA")
        println("5. Find student with highest GPA")
        println("6. Remove student")
        println("7. Đếm SV GPA >= 8.0")
        println("8. Đếm SV GPA < 5.0")
        println("9. Tìm SV lớn tuổi nhất")
        println("10. Tìm SV có GPA trong khoảng 7.0 - 8.5")
        println("11. Tìm SV theo ngành")
        println("12. Sắp xếp SV theo GPA giảm dần")
        println("13. Top 3 SV có GPA cao nhất")
        println("14. Sắp xếp SV theo tuổi")
        println("15. Sắp xếp SV theo tên")
        println("16. Tìm theo mã sinh viên")
        println("17. Sắp xếp tên A đến Z")
        println("18. Thống kê Pass (GPA>=4) / Fail (GPA<4)")
        println("0. Exit")
        println("========================================")
        print("Choose: ")

        val choice = readLine()?.trim()

        if (choice == "1") {
            print("ID: "); val id = readLine().orEmpty()
            print("Họ tên: "); val name = readLine().orEmpty()
            val age = readInt("Tuổi: ")
            print("Ngành: "); val major = readLine().orEmpty()
            val gpa = readGpa("GPA (0 -> 10): ")
            manager.addStudent(Student(id, name, age, major, gpa))
        } else if (choice == "2") {
            manager.displayAll()
        } else if (choice == "3") {
            println("Tìm theo: 1-ID  2-Tên")
            val sub = readLine()?.trim()
            if (sub == "1") {
                print("Nhập ID: ")
                val id = readLine().orEmpty()
                val s = manager.searchById(id)
                if (s != null) printStudentList(listOf(s)) else println("Không tìm thấy.")
            } else if (sub == "2") {
                print("Nhập tên (hoặc một phần tên): ")
                val keyword = readLine().orEmpty()
                printStudentList(manager.searchByName(keyword))
            } else {
                println("Lựa chọn không hợp lệ.")
            }
        } else if (choice == "4") {
            print("Nhập ngành cần tính GPA trung bình: ")
            val major = readLine().orEmpty()
            println("GPA trung bình ngành $major: ${manager.averageGpaByMajor(major)}")
        } else if (choice == "5") {
            val s = manager.highestGpaStudent()
            if (s != null) printStudentList(listOf(s)) else println("Danh sách trống.")
        } else if (choice == "6") {
            print("Nhập ID sinh viên cần xóa: ")
            val id = readLine().orEmpty()
            if (manager.removeStudent(id)) println("Đã xóa sinh viên $id")
            else println("Không tìm thấy sinh viên.")
        } else if (choice == "7") {
            println("Số SV có GPA >= 8.0: ${manager.countGpaAbove8()}")
        } else if (choice == "8") {
            println("Số SV có GPA < 5.0: ${manager.countGpaBelow5()}")
        } else if (choice == "9") {
            val s = manager.oldestStudent()
            if (s != null) printStudentList(listOf(s)) else println("Danh sách trống.")
        } else if (choice == "10") {
            printStudentList(manager.studentsInGpaRange())
        } else if (choice == "11") {
            print("Nhập ngành cần tìm: ")
            val major = readLine().orEmpty()
            printStudentList(manager.studentsByMajor(major))
        } else if (choice == "12") {
            printStudentList(manager.sortByGpaDescending())
        } else if (choice == "13") {
            printStudentList(manager.top3ByGpa())
        } else if (choice == "14") {
            printStudentList(manager.sortByAge())
        } else if (choice == "15") {
            printStudentList(manager.sortByName())
        } else if (choice == "16") {
            print("Nhập mã sinh viên: ")
            val id = readLine().orEmpty()
            val s = manager.searchById(id)
            if (s != null) printStudentList(listOf(s))
            else println("Không tìm thấy sinh viên có mã $id")
        } else if (choice == "17") {
            printStudentList(manager.sortByNameAZ())
        } else if (choice == "18") {
            val (pass, fail) = manager.countPassFail()
            println("Pass (GPA >= 4.0): $pass")
            println("Fail (GPA < 4.0): $fail")
        } else if (choice == "0") {
            println("Thoát chương trình.")
            return
        } else {
            println("Lựa chọn không hợp lệ, vui lòng thử lại.")
        }
    }
}