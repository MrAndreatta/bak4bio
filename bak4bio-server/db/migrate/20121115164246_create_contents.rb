class CreateContents < ActiveRecord::Migration
  def change
    create_table :contents do |t|
      t.has_attached_file :source
      t.string :description
      t.integer :owner_id

      t.timestamps
    end
    add_index(:contents, :owner_id)
  end
end
