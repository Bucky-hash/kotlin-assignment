import kotlin.math.round

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

    fun searchById(id: String): Student? = students.find { it.id == id }

    fun searchByName(keyword: String): List<Student> =
        students.filter { it.fullName.contains(keyword, ignoreCase = true) }

    fun removeStudent(id: String): Boolean {
        val s = searchById(id) ?: return false
        students.remove(s)
        return true
    }

    fun countGpaAbove8(): Int = students.count { it.gpa >= 8.0 }

    fun countGpaBelow5(): Int = students.count { it.gpa < 5.0 }

    fun averageGpaByMajor(major: String): Double {
        val list = students.filter { it.major.equals(major, ignoreCase = true) }
        if (list.isEmpty()) return 0.0
        return round(list.map { it.gpa }.average() * 100) / 100
    }

    fun highestGpaStudent(): Student? = students.maxByOrNull { it.gpa }

    fun oldestStudent(): Student? = students.maxByOrNull { it.age }

    fun studentsInGpaRange(min: Double = 7.0, max: Double = 8.5): List<Student> =
        students.filter { it.gpa in min..max }

    fun studentsByMajor(major: String): List<Student> =
        students.filter { it.major.equals(major, ignoreCase = true) }

    fun sortByGpaDescending(): List<Student> = students.sortedByDescending { it.gpa }

    fun top3ByGpa(): List<Student> = sortByGpaDescending().take(3)

    fun sortByAge(): List<Student> = students.sortedBy { it.age }

    fun sortByName(): List<Student> = students.sortedBy { it.fullName.lowercase() }

    fun sortByNameAZ(): List<Student> = students.sortedBy { it.fullName.lowercase() }

    fun countPassFail(): Pair<Int, Int> {
        val pass = students.count { it.gpa >= 4.0 }
        val fail = students.count { it.gpa < 4.0 }
        return Pair(pass, fail)
    }

    fun loadSampleData() {
        addStudent(Student("SV001", "Nguyen Van Tu", 20, "Công nghệ thông tin", 8.5))
        addStudent(Student("SV002", "Tran Thi Tra", 21, "Kế toán", 6.8))
        addStudent(Student("SV003", "Le Van Cuong", 22, "Công nghệ thông tin", 4.5))
        addStudent(Student("SV004", "Pham Thi Thuy", 19, "Kế toán", 7.9))
        addStudent(Student("SV005", "Hoang Van Em", 23, "Công nghệ thông tin", 9.2))
    }
}

fun printStudentList(list: List<Student>) {
    if (list.isEmpty()) {
        println("Không tìm thấy sinh viên nào.")
        return
    }
    println("%-8s %-20s %-5s %-15s %-5s".format("ID", "Họ tên", "Tuổi", "Ngành", "GPA"))
    list.forEach {
        println("%-8s %-20s %-5d %-15s %-5.2f".format(it.id, it.fullName, it.age, it.major, it.gpa))
    }
}

fun readInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val value = readLine()?.toIntOrNull()
        if (value != null) return value
        println("Giá trị không hợp lệ, vui lòng nhập lại.")
    }
}

fun readGpa(prompt: String): Double {
    while (true) {
        print(prompt)
        val value = readLine()?.toDoubleOrNull()
        when {
            value == null -> println("Giá trị không hợp lệ, vui lòng nhập lại.")
            value < 0.0 || value > 10.0 -> println("GPA phải nằm trong khoảng 0 -> 10, vui lòng nhập lại.")
            else -> return value
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

        when (readLine()?.trim()) {
            "1" -> {
                print("ID: "); val id = readLine().orEmpty()
                print("Họ tên: "); val name = readLine().orEmpty()
                val age = readInt("Tuổi: ")
                print("Ngành: "); val major = readLine().orEmpty()
                val gpa = readGpa("GPA (0 -> 10): ")
                manager.addStudent(Student(id, name, age, major, gpa))
            }
            "2" -> manager.displayAll()
            "3" -> {
                println("Tìm theo: 1-ID  2-Tên")
                when (readLine()?.trim()) {
                    "1" -> {
                        print("Nhập ID: ")
                        val s = manager.searchById(readLine().orEmpty())
                        if (s != null) printStudentList(listOf(s)) else println("Không tìm thấy.")
                    }
                    "2" -> {
                        print("Nhập tên (hoặc một phần tên): ")
                        printStudentList(manager.searchByName(readLine().orEmpty()))
                    }
                    else -> println("Lựa chọn không hợp lệ.")
                }
            }
            "4" -> {
                print("Nhập ngành cần tính GPA trung bình: ")
                val major = readLine().orEmpty()
                println("GPA trung bình ngành $major: ${manager.averageGpaByMajor(major)}")
            }
            "5" -> {
                val s = manager.highestGpaStudent()
                if (s != null) printStudentList(listOf(s)) else println("Danh sách trống.")
            }
            "6" -> {
                print("Nhập ID sinh viên cần xóa: ")
                val id = readLine().orEmpty()
                if (manager.removeStudent(id)) println("Đã xóa sinh viên $id")
                else println("Không tìm thấy sinh viên.")
            }
            "7" -> println("Số SV có GPA >= 8.0: ${manager.countGpaAbove8()}")
            "8" -> println("Số SV có GPA < 5.0: ${manager.countGpaBelow5()}")
            "9" -> {
                val s = manager.oldestStudent()
                if (s != null) printStudentList(listOf(s)) else println("Danh sách trống.")
            }
            "10" -> printStudentList(manager.studentsInGpaRange())
            "11" -> {
                print("Nhập ngành cần tìm: ")
                printStudentList(manager.studentsByMajor(readLine().orEmpty()))
            }
            "12" -> printStudentList(manager.sortByGpaDescending())
            "13" -> printStudentList(manager.top3ByGpa())
            "14" -> printStudentList(manager.sortByAge())
            "15" -> printStudentList(manager.sortByName())
            "16" -> {
                print("Nhập mã sinh viên: ")
                val id = readLine().orEmpty()
                val s = manager.searchById(id)
                if (s != null) printStudentList(listOf(s))
                else println("Không tìm thấy sinh viên có mã $id")
            }
            "17" -> printStudentList(manager.sortByNameAZ())
            "18" -> {
                val (pass, fail) = manager.countPassFail()
                println("Pass (GPA >= 4.0): $pass")
                println("Fail (GPA < 4.0): $fail")
            }
            "0" -> {
                println("Thoát chương trình.")
                return
            }
            else -> println("Lựa chọn không hợp lệ, vui lòng thử lại.")
        }
    }
}
