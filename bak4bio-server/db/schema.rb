# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20130209194949) do

  create_table "blasts", :force => true do |t|
    t.integer  "owner_id"
    t.string   "title"
    t.integer  "entry_id"
    t.datetime "start_at"
    t.string   "status"
    t.datetime "end_at"
    t.integer  "output_id"
    t.string   "program"
    t.string   "database"
    t.integer  "word_size"
    t.integer  "max_target_sequence"
    t.decimal  "expect",              :precision => 10, :scale => 0
    t.decimal  "max_matches_range",   :precision => 10, :scale => 0
    t.string   "m_scores"
    t.string   "gap_costs"
    t.datetime "created_at",                                         :null => false
    t.datetime "updated_at",                                         :null => false
  end

  add_index "blasts", ["entry_id"], :name => "index_blasts_on_entry_id"
  add_index "blasts", ["output_id"], :name => "index_blasts_on_output_id"
  add_index "blasts", ["owner_id"], :name => "index_blasts_on_owner_id"

  create_table "contents", :force => true do |t|
    t.string   "source_file_name"
    t.string   "source_content_type"
    t.integer  "source_file_size"
    t.datetime "source_updated_at"
    t.string   "description"
    t.integer  "owner_id"
    t.datetime "created_at",          :null => false
    t.datetime "updated_at",          :null => false
  end

  add_index "contents", ["owner_id"], :name => "index_contents_on_owner_id"

  create_table "users", :force => true do |t|
    t.string   "name"
    t.string   "email",                  :default => "", :null => false
    t.string   "encrypted_password",     :default => "", :null => false
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count",          :default => 0
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string   "current_sign_in_ip"
    t.string   "last_sign_in_ip"
    t.string   "confirmation_token"
    t.datetime "confirmed_at"
    t.datetime "confirmation_sent_at"
    t.string   "authentication_token"
    t.datetime "created_at",                             :null => false
    t.datetime "updated_at",                             :null => false
  end

  add_index "users", ["authentication_token"], :name => "index_users_on_authentication_token", :unique => true
  add_index "users", ["confirmation_token"], :name => "index_users_on_confirmation_token", :unique => true
  add_index "users", ["email"], :name => "index_users_on_email", :unique => true
  add_index "users", ["reset_password_token"], :name => "index_users_on_reset_password_token", :unique => true

end
